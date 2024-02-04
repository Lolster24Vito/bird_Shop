package hr.algebra.bird_shop.controller;

import hr.algebra.bird_shop.repository.BirdOrderRepository;
import hr.algebra.bird_shop.repository.UserLoginInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final UserLoginInfoRepository userLoginInfoRepository;
    private final BirdOrderRepository birdOrderRepository;
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
}
