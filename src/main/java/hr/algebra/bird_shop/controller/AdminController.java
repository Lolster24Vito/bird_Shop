package hr.algebra.bird_shop.controller;

import hr.algebra.bird_shop.domain.Bird;
import hr.algebra.bird_shop.domain.BirdTag;
import hr.algebra.bird_shop.repository.BirdOrderRepository;
import hr.algebra.bird_shop.repository.BirdRepository;
import hr.algebra.bird_shop.repository.BirdTagRepository;
import hr.algebra.bird_shop.repository.UserLoginInfoRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final BirdRepository birdRepository;
    private final BirdTagRepository birdTagRepository;
    private final UserLoginInfoRepository userLoginInfoRepository;
    private final BirdOrderRepository birdOrderRepository;

    @ModelAttribute
    public void addAAttributeToModel(Model model) {
        // Add the cookieCart attribute to the model for every request in this controller
        model.addAttribute("createBird",new Bird());
        model.addAttribute("birds",birdRepository.findAll());
        model.addAttribute("birdTags",birdTagRepository.findAll());
    }
    @GetMapping("/loginHistory")
    public String loginHistory(Model model){
        model.addAttribute("loginInfos",userLoginInfoRepository.findAll());
        return "loginHistory";
    }


    @GetMapping("/paymentHistory")
    public String paymentHistory(Model model,@RequestParam(value = "searchUser", required = false) String searchUser,
  @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
   @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
                                 ){
        if((searchUser!=null&&!searchUser.isEmpty())
                ||(startDate != null && endDate != null)){
            if(searchUser!=null&&!searchUser.isEmpty()){
                model.addAttribute("orders",birdOrderRepository.findAllByBirdUser_UsernameIgnoreCaseContaining(searchUser));
            }
         else if (startDate != null && endDate != null) {
                model.addAttribute("orders", birdOrderRepository.findAllByCreatedTimeBetween(startDate, endDate));
            }
        }
        else{
            model.addAttribute("orders",birdOrderRepository.findAll());
        }

        return "paymentHistory";
    }
    @GetMapping("/addTags")
    public String addTags(Model model){
        model.addAttribute("birdTags",birdTagRepository.findAll());
        model.addAttribute("createTag",new BirdTag());
        return "addTags";
    }
    @PostMapping("/saveNewTag")
    public String saveNewTag(@Valid @ModelAttribute ("createTag") BirdTag createTag ,
                             BindingResult bindingResult, Model model ){
        if(bindingResult.hasErrors()){
            model.addAttribute("birdTags",birdTagRepository.findAll());
            return "addTags";
        }
        else{
            birdTagRepository.save(createTag);
            return "redirect:/admin/addTags";
        }

    }
    @GetMapping("/home")
    public String adminHome(Model model){
        return "adminHome";
    }
    private void addHomePageBirdAttributes(Model model){
        model.addAttribute("birds",birdRepository.findAll());
        model.addAttribute("birdTags",birdTagRepository.findAll());
    }

    @PostMapping("/saveNewBird")
    public String saveNewBird(@Valid @ModelAttribute("createBird")  Bird createBird, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            addHomePageBirdAttributes(model);
            return "adminHome";
        }
        else{
            birdRepository.save(createBird);
            return "redirect:/admin/home";
        }

    }

}
