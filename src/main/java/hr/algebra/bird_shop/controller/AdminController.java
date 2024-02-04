package hr.algebra.bird_shop.controller;

import hr.algebra.bird_shop.repository.UserLoginInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserLoginInfoRepository userLoginInfoRepository;
    @GetMapping("/loginHistory")
    public String adminPage(Model model){
        model.addAttribute("loginInfos",userLoginInfoRepository.findAll());
        return "loginHistory";
    }
}
