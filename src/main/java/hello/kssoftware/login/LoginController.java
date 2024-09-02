package hello.kssoftware.login;

import hello.kssoftware.login.dto.MemberCreateDto;
import hello.kssoftware.login.dto.MemberLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;
    private final JpaMemberRepository jpaMemberRepository;

    @GetMapping("/")
    public String home(@SessionAttribute(name = "loginUser", required = false) Member loginUser) {
        if (loginUser != null) {
            return "redirect:/";
        } else{
            return "redirect:/login/signIn";
        }
    }

    @GetMapping("/signIn")
    public String signInForm(Model model){
        model.addAttribute("memberLoginDto", new MemberLoginDto());
        return "login/signIn";
    }

    @PostMapping("/signIn")
    public String signIn(@ModelAttribute MemberLoginDto memberLoginDto,
                         RedirectAttributes redirectAttributes,
                         HttpServletRequest request) {
        boolean isValid = memberService.validateLogin(memberLoginDto.getId(), memberLoginDto.getPassword());

        if (!isValid) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/login/signIn";
        } else {
            Optional<Member> memberOptional = jpaMemberRepository.findUserId(memberLoginDto.getId());
            Member loginUser = memberOptional.get();

            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginUser);

            return "redirect:/";
        }
    }

    @GetMapping("/signUp")
    public String signupForm(@ModelAttribute MemberCreateDto memberCreateDto) {
        return "login/signUp";
    }

    @PostMapping("/signUp")
    public String signup(@ModelAttribute MemberCreateDto memberCreateDto,
                         RedirectAttributes redirectAttributes,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/signUp";
        }
        if (memberService.isUserIdExists(memberCreateDto.getId())) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/login/signUp";
        }
        if (memberService.isUserNameExists(memberCreateDto.getName())) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/login/signUp";
        }

        memberService.join(Member.createMember(memberCreateDto));
        return "index";
    }

    @GetMapping("/myPage")
    public String myPage(Model model) {
        model.addAttribute("isLoggedIn", true);
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
