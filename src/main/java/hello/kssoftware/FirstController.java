package hello.kssoftware;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FirstController {

    @GetMapping("/")
    public String test() {
        return "First Controller";
    }

    @GetMapping("/showMe")
    public List<String> hello() {
        return Arrays.asList("민우 인사");
    }
}
