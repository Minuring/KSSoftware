package hello.kssoftware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class FirstController {

    @RequestMapping
    public String welcomePage() {
        return "index";
    }

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        boolean isLoggedIn = false;

        if (session.getAttribute("isLoggedIn") == null) {
            model.addAttribute("isLoggedIn", isLoggedIn);
        }else {
            isLoggedIn = true;
        }

        model.addAttribute("isLoggedIn", isLoggedIn);

        return "index";
    }

}
