package hello.kssoftware.login;

import hello.kssoftware.FlashNotifier;
import hello.kssoftware.board.dto.BoardDto;
import hello.kssoftware.board.dto.CommentDto;
import hello.kssoftware.board.entity.Board;
import hello.kssoftware.board.service.BoardService;
import hello.kssoftware.login.argumentresolver.Login;
import hello.kssoftware.login.dto.MemberCreateDto;
import hello.kssoftware.login.dto.MemberLoginDto;
import hello.kssoftware.login.dto.NameChangeDto;
import hello.kssoftware.login.dto.PasswordChangeDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final FlashNotifier flashNotifier;
    private final BoardService boardService;

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
        Member loginUser = memberOptional.get();

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
    public String signup(@Validated @ModelAttribute MemberCreateDto memberCreateDto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "login/signUp";
        }

        Member member = Member.createMember(memberCreateDto.getId(),
                memberCreateDto.getName(),
                memberCreateDto.getPassword(),
                memberCreateDto.getNumber());
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


    private void setMyPageModel(final Member member, final Model model, final PasswordChangeDto passwordChangeDto, final NameChangeDto nameChangeDto) {
        List<Board> all = boardService.findAll(new BoardDto.Search());
        List<BoardDto.Response> boards = all.stream().filter(b -> b.getWriter().equals(member)).map(BoardDto.Response::new).toList();
        List<CommentDto.Response> comments = all.stream().flatMap(b -> b.getComments().stream())
                .filter(c -> c.getWriter().equals(member)).map(CommentDto.Response::new).toList();

        model.addAttribute("member", member);
        model.addAttribute("passwordChangeDto", passwordChangeDto);
        model.addAttribute("nameChangeDto", nameChangeDto);
        model.addAttribute("boards", boards);
        model.addAttribute("comments", comments);
    }
}
