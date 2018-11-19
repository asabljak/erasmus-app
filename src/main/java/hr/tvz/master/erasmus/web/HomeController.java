package hr.tvz.master.erasmus.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String getHomepage() {
        return "template";
    }

    @GetMapping("/login")
    public String getLoginPAge() {
        return "/login/login";
    }
}
