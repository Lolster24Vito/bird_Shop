package hr.algebra.bird_shop.controller;

import hr.algebra.bird_shop.domain.BirdOrder;
import hr.algebra.bird_shop.domain.BirdUser;
import hr.algebra.bird_shop.repository.BirdOrderRepository;
import hr.algebra.bird_shop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class UserOrdersController {

    private final UserRepository userRepository;
    private final BirdOrderRepository birdOrderRepository;

    @GetMapping("/userDetails")
    public String userDetails(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BirdUser birdUser = userRepository.findByUsername(authentication.getName());
        List<BirdOrder> userBirdOrders = birdUser.getUserBirdOrders();
        model.addAttribute("orders",userBirdOrders);
        return "userDetails";
    }
}
