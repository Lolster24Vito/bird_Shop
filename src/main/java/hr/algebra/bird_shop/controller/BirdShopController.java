package hr.algebra.bird_shop.controller;

import hr.algebra.bird_shop.repository.BirdRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class BirdShopController {
    private final BirdRepository birdRepository;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("birds",birdRepository.findAll());
        //        model.addAttribute("lego", new Lego());
        return "home";
    }

}
