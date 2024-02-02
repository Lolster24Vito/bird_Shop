package hr.algebra.bird_shop.controller;

import hr.algebra.bird_shop.domain.Bird;
import hr.algebra.bird_shop.domain.BirdUser;
import hr.algebra.bird_shop.domain.Role;
import hr.algebra.bird_shop.repository.RoleRepository;
import hr.algebra.bird_shop.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
public class RegisterController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterController(UserRepository userRepository,
                              RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("createBirdUser", new BirdUser());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("createBirdUser") BirdUser createBirdUser, BindingResult bindingResult,
                           Model model){
        if(bindingResult.hasErrors()){
            return "register";
        }
        else{
            Role roleUser = roleRepository.findByNameEquals("ROLE_USER");
            createBirdUser.setRoles(Collections.singleton(roleUser));
            createBirdUser.setPassword( passwordEncoder.encode(createBirdUser.getPassword()));
            userRepository.save(createBirdUser);
            return "redirect:/";
        }

    }
}
