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
import org.springframework.security.core.Authentication;
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
    public void addAAttributeToModel(Authentication authentication,Model model) {
        // Add the cookieCart attribute to the model for every request in this controller
        model.addAttribute("createBird",new Bird());
        model.addAttribute("birds",birdRepository.findAll());
        model.addAttribute("birdTags",birdTagRepository.findAll());
        if (authentication != null && authentication.isAuthenticated()) {
            // Add the authentication object to the model if needed
            model.addAttribute("authentication", authentication);
        }
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

    @GetMapping("/home")
    public String adminHome(Authentication authentication, Model model){

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
    @GetMapping("/updateBird/{id}")
    public String showUpdateBird(@PathVariable("id") long id,Model model) {
        Bird bird = birdRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bird Id:" + id));


        model.addAttribute("bird", bird);
        return "editBird";
    }
    @PostMapping("/updateBird/{id}")
    public String updateBird(@PathVariable("id") long id, @Valid Bird bird,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            bird.setId(id);
            return "editBird";
        }

        birdRepository.save(bird);
        return "redirect:/admin/home";
    }
    @GetMapping("/deleteBird/{id}")
    public String deleteBird(@PathVariable("id") long id, Model model) {
        Bird bird = birdRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bird Id:" + id));
        birdRepository.delete(bird);
        return "redirect:/admin/home";
    }

    //tags
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
    @GetMapping("/updateTag/{id}")
    public String showUpdateTag(@PathVariable("id") long id,Model model) {
        BirdTag tag = birdTagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Tag Id:" + id));


        model.addAttribute("birdTag", tag);
        return "editTag";
    }
    @PostMapping("/updateTag/{id}")
    public String updateTag(@PathVariable("id") long id, @Valid BirdTag tag,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            tag.setId(id);
            return "editTag";
        }

        birdTagRepository.save(tag);
        return "redirect:/admin/addTags";
    }
    @GetMapping("/deleteTag/{id}")
    public String deleteTag(@PathVariable("id") long id, Model model) {
        BirdTag tag = birdTagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid bird Id:" + id));
        Iterable<Bird> birds=birdRepository.findAll();
        for (Bird bird:birds){
            if(bird.getBirdTags().contains(tag)){
                bird.getBirdTags().remove(tag);
                birdRepository.save(bird);
            }
        }
        birdTagRepository.delete(tag);
        return "redirect:/admin/addTags";
    }

}
