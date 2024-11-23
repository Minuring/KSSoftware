package hello.kssoftware.login;

import hello.kssoftware.FlashNotifier;
import hello.kssoftware.board.comment.dto.CommentResponse;
import hello.kssoftware.board.common.BoardRepository;
import hello.kssoftware.board.common.BoardResponse;
import hello.kssoftware.login.argumentresolver.Login;
import hello.kssoftware.login.dto.NameChange;
import hello.kssoftware.login.dto.PasswordChange;
import hello.kssoftware.login.dto.SignIn;
import hello.kssoftware.login.dto.SignUp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final FlashNotifier flashNotifier;
    private final BoardRepository boardRepository;

    @GetMapping("/signIn")
    public String signInForm(SignIn signIn) {
        return "login/signIn";
    }

    @PostMapping("/signIn")
    public String signIn(@Validated SignIn signIn,
                         BindingResult bindingResult,
                         @RequestParam(defaultValue = "/") String redirectURI,
                         HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/signIn";
        }

        Optional<Member> memberOptional = memberRepository.findById(signIn.getId());
        Member loginUser = memberOptional.orElseThrow(IllegalArgumentException::new);

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", loginUser);

        flashNotifier.notify("message.login.signIn.success");
        return "redirect:" + redirectURI;
    }

    @GetMapping("/signUp")
    public String signupForm(@ModelAttribute SignUp signUp) {
        return "login/signUp";
    }

    @PostMapping("/signUp")
    public String signup(@Validated @ModelAttribute SignUp dto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/signUp";
        }
        memberService.join(dto);

        return "index";
    }

    @GetMapping("/myPage")
    public String myPage(@Login Member member, Model model) {
        setMyPageModel(member, model, new PasswordChange(), new NameChange());
        return "login/myPage";
    }

    @PostMapping("/myPage/changePassword")
    public String changePassword(@Login Member member, @Validated PasswordChange dto, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            setMyPageModel(member, model, dto, new NameChange());
            return "login/myPage";
        }

        memberService.updatePassword(member.getId(), dto.getNewPassword());
        session.invalidate();

        flashNotifier.notify("message.updated.password");
        flashNotifier.notify("message.required.reLogin");
        return "redirect:/";
    }

    @PostMapping("/myPage/changeName")
    public String changeName(@Login Member member, @Validated NameChange dto, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            setMyPageModel(member, model, new PasswordChange(), dto);
            return "login/myPage";
        }

        memberService.updateName(member.getId(), dto.getName());
        session.invalidate();

        flashNotifier.notify("message.updated.name");
        flashNotifier.notify("message.required.reLogin");
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        Optional.ofNullable(request.getSession(false))
                .ifPresent(HttpSession::invalidate);
        return "redirect:/";
    }


    private void setMyPageModel(Member member, Model model, PasswordChange passwordChange, NameChange nameChange) {
        List<BoardResponse> boards = boardRepository.findByMember(member).stream().map(BoardResponse::from).toList();
        List<CommentResponse> comments = getCommentResponses(member);

        model.addAttribute("member", member);
        model.addAttribute("passwordChange", passwordChange);
        model.addAttribute("nameChange", nameChange);
        model.addAttribute("boards", boards);
        model.addAttribute("comments", comments);
    }

    private List<CommentResponse> getCommentResponses(Member member) {
        return boardRepository.findByMember(member)
                .stream()
                .flatMap(b -> b.getComments().stream())
                .filter(c -> c.getWriter().equals(member))
                .map(CommentResponse::from)
                .toList();
    }
}
