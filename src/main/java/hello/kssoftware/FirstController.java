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
        System.out.println("fetchTest_dokyun_local_test");
        System.out.println("fetchTest_minu");
        System.out.println("fetchTest_minu2");
        System.out.println("fetchTest_dokyun");
        return Arrays.asList("도균 인사");
    }

    public static void main(String[] args) {
        System.out.println("testFetch");
    }
}
