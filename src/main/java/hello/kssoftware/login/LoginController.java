package hello.kssoftware.login;

import hello.kssoftware.login.dto.MemberCreateDto;
import hello.kssoftware.login.dto.MemberLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/signIn")
    public String signInForm(Model model) {
        model.addAttribute("memberLoginDto", new MemberLoginDto());
        return "login/signIn";
    }

    @PostMapping("/signIn")
    public String signIn(@Validated @ModelAttribute MemberLoginDto memberLoginDto,
                         BindingResult bindingResult,
                         @RequestParam(defaultValue = "/") String redirectURI,
                         HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/signIn";
        }

        Optional<Member> memberOptional = memberRepository.findUserId(memberLoginDto.getId());
        Member loginUser = memberOptional.get();

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", loginUser);

        return "redirect:" + redirectURI;
    }

    @GetMapping("/signUp")
    public String signupForm(Model model) {
        model.addAttribute("memberCreateDto", new MemberCreateDto());
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
    public String myPage() {
        return "login/myPage";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
