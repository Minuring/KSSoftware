package hello.kssoftware.login;

import hello.kssoftware.login.dto.MemberCreateDto;
import hello.kssoftware.login.dto.MemberLoginDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private JpaMemberRepository jpaMemberRepository;

    @GetMapping("/")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("loginUser") != null) {
            return "redirect:/";
        } else{
            return "redirect:/login/signin";
        }
    }

    @GetMapping("/signin")  //로그인
    public String signinForm(Model model){
        model.addAttribute("memberLoginDto", new MemberLoginDto());
        return "login/signin";
    }

    @PostMapping("/signin")
    public String signin(@ModelAttribute MemberLoginDto memberLoginDto,
                         RedirectAttributes redirectAttributes,
                         HttpServletRequest request) {
        boolean isValid = memberService.validateLogin(memberLoginDto.getUserId(), memberLoginDto.getPwd());

        if (!isValid) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/login/signin";
        } else {
            //로그인 성공
            Optional<Member> checkLogin = jpaMemberRepository.findUserId(memberLoginDto.getUserId());
            Member loginUser = checkLogin.get();

            //세션
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginUser);

            return "redirect:/";
        }
    }

    @GetMapping("/signup")  //회원가입
    public String signupForm(Model model){
        model.addAttribute("memberCreateDto", new MemberCreateDto());
        return "login/signup";
    }

    @PostMapping("/signup") //회원가입
    public String signup(@ModelAttribute Member member, RedirectAttributes redirectAttributes) {
        //userId 중복 확인
        if (memberService.isUserIdExists(member.getUserId())) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/login/signup";
        }
        //userName 중복 확인
        if (memberService.isUserNameExists(member.getUserName())) {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/login/signup";
        }

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String logout(Model model) {
        model.addAttribute("isLoggedIn", true);
        return "login/mypage";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        //세션 초기화
        request.getSession().invalidate();
        //쿠키 초기화
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return "redirect:/";
    }
}
