package hr.algebra.bird_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthOnlyPagesController {
    @GetMapping("/adminPage")
    public String adminPage(){
        return "AdminPageTest";
    }
    @GetMapping("/userPage")
    public String userPage(){
        return "UserPageTest";
    }
}
