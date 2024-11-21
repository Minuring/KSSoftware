package hello.kssoftware.login;

import hello.kssoftware.FlashNotifier;
import hello.kssoftware.board.comment.dto.CommentResponse;
import hello.kssoftware.board.common.Board;
import hello.kssoftware.board.common.BoardRepository;
import hello.kssoftware.login.argumentresolver.Login;
import hello.kssoftware.login.dto.MemberCreateDto;
import hello.kssoftware.login.dto.MemberLoginDto;
import hello.kssoftware.login.dto.NameChangeDto;
import hello.kssoftware.login.dto.PasswordChangeDto;
import hello.kssoftware.login.encrypt.PasswordEncoder;
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
    public String signInForm(MemberLoginDto memberLoginDto) {
        return "login/signIn";
    }

    @PostMapping("/signIn")
    public String signIn(@Validated MemberLoginDto memberLoginDto,
                         BindingResult bindingResult,
                         @RequestParam(defaultValue = "/") String redirectURI,
                         HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/signIn";
        }

        Optional<Member> memberOptional = memberRepository.findById(memberLoginDto.getId());
        Member loginUser = memberOptional.orElseThrow(IllegalArgumentException::new);

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", loginUser);

        flashNotifier.notify("message.login.signIn.success");
        return "redirect:" + redirectURI;
    }

    @GetMapping("/signUp")
    public String signupForm(@ModelAttribute MemberCreateDto memberCreateDto) {
        return "login/signUp";
    }

    @PostMapping("/signUp")
    public String signup(@Validated @ModelAttribute MemberCreateDto dto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "login/signUp";
        }

        String salt = PasswordEncoder.getSalt();
        String encodedPassword = PasswordEncoder.encode(dto.getPassword(), salt);
        Member member = Member.createMember(dto.getId(),
                dto.getName(),
                encodedPassword,
                salt,
                dto.getNumber());
        memberService.join(member);

        return "index";
    }

    @GetMapping("/myPage")
    public String myPage(@Login Member member, Model model) {
        setMyPageModel(member, model, new PasswordChangeDto(), new NameChangeDto());
        return "login/myPage";
    }

    @PostMapping("/myPage/changePassword")
    public String changePassword(@Login Member member, @Validated PasswordChangeDto dto, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            setMyPageModel(member, model, dto, new NameChangeDto());
            return "login/myPage";
        }

        memberService.updatePassword(member.getId(), dto.getNewPassword());

        flashNotifier.notify("message.updated.password");
        flashNotifier.notify("message.required.reLogin");
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/myPage/changeName")
    public String changeName(@Login Member member, @Validated NameChangeDto dto, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            setMyPageModel(member, model, new PasswordChangeDto(), dto);
            return "login/myPage";
        }

        memberService.updateName(member.getId(), dto.getName());

        flashNotifier.notify("message.updated.name");
        flashNotifier.notify("message.required.reLogin");
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }


    private void setMyPageModel(Member member, Model model, PasswordChangeDto passwordChangeDto, NameChangeDto nameChangeDto) {
        List<Board> boards = boardRepository.findByMember(member);
        List<CommentResponse> comments = getCommentResponses(member);

        model.addAttribute("member", member);
        model.addAttribute("passwordChangeDto", passwordChangeDto);
        model.addAttribute("nameChangeDto", nameChangeDto);
        model.addAttribute("boards", boards);
        model.addAttribute("comments", comments);
    }

    private List<CommentResponse> getCommentResponses(Member member) {
        List<Board> boards = boardRepository.findByMember(member);

        return boards.stream()
                .flatMap(b -> b.getComments().stream())
                .filter(c -> c.getWriter().equals(member))
                .map(CommentResponse::from)
                .toList();
    }
}
