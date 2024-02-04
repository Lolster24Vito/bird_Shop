package hr.algebra.bird_shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.algebra.bird_shop.domain.Bird;
import hr.algebra.bird_shop.domain.BirdShopItem;
import hr.algebra.bird_shop.domain.BirdTag;
import hr.algebra.bird_shop.domain.Cart;
import hr.algebra.bird_shop.repository.BirdRepository;
import hr.algebra.bird_shop.repository.BirdTagRepository;
import hr.algebra.bird_shop.util.CookieCartUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;



@Controller
@AllArgsConstructor
public class BirdShopController {

    private final BirdRepository birdRepository;
    private final BirdTagRepository birdTagRepository;
    private final CookieCartUtil cookieCartUtil;

    @GetMapping({"/", "home"})
    public String home(Authentication authentication,Model model, HttpServletRequest request){
        //addHomePageBirdAttributes(model);
       // model.addAttribute("createBird",new Bird());
       // model.addAttribute("cookieCart",getCookieCart(request));

        if (authentication != null && authentication.isAuthenticated()) {
            // Add the authentication object to the model if needed
            model.addAttribute("authentication", authentication);
        }
        return "home";
    }
    @ModelAttribute
    public void addAAttributeToModel(Model model, HttpServletRequest request) {
        // Add the cookieCart attribute to the model for every request in this controller
        model.addAttribute("createBird",new Bird());
        model.addAttribute("birds",birdRepository.findAll());
        model.addAttribute("birdTags",birdTagRepository.findAll());
        model.addAttribute("cookieCart",cookieCartUtil.getCookieCart(request));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/saveNewBird")
    public String saveNewBird(@Valid @ModelAttribute("createBird")  Bird createBird, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            addHomePageBirdAttributes(model);
            return "home";
        }
        else{
            birdRepository.save(createBird);
            return "redirect:/";
        }

    }
    private void addHomePageBirdAttributes(Model model){
        model.addAttribute("birds",birdRepository.findAll());
        model.addAttribute("birdTags",birdTagRepository.findAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("addTags")
    public String addTags(Model model){
        model.addAttribute("birdTags",birdTagRepository.findAll());
        model.addAttribute("createTag",new BirdTag());
        return "addTags";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/saveNewTag")
    public String saveNewTag(@Valid @ModelAttribute ("createTag") BirdTag createTag ,
                             BindingResult bindingResult,Model model ){
        if(bindingResult.hasErrors()){
            model.addAttribute("birdTags",birdTagRepository.findAll());
            return "addTags";
        }
        else{
            birdTagRepository.save(createTag);
            return "redirect:/addTags";
        }

    }
    @PostMapping("/addBirdToCart/{id}")
    public String addBirdToCart(HttpServletResponse response,HttpServletRequest request, @RequestParam Integer quantity, @PathVariable Long id)
            throws JsonProcessingException {
        BirdShopItem shopItemToAdd=new BirdShopItem();
        Optional<Bird> birdGet = birdRepository.findById(id);
        shopItemToAdd.setQuantity(quantity);

        if(birdGet.isEmpty())return "redirect:/home";
        shopItemToAdd.setBirdProduct(birdGet.get());

        Cart cart=cookieCartUtil.getCookieCart(request);

        cart.addItem(shopItemToAdd);
            //add new cookie

        String encodedCartString=cookieCartUtil.encodeCart(cart);
            Cookie cookie=new Cookie("cart",encodedCartString);
            cookie.setPath("/");
        //30min
             cookie.setMaxAge(60*30);
            response.addCookie(cookie);
        return "redirect:/";
    }
    @PostMapping("/removeEverythingFromCart")
    public String removeEverythingFromCart(HttpServletResponse response){
        cookieCartUtil.deleteCartCookies(response);

        return "redirect:/home";
        //return "redirect:/";
    }
    @PostMapping("/removeBirdFromCartQuantity/{id}")
    public String removeBirdFromCartQuantity(HttpServletResponse response,HttpServletRequest request,
                                             @RequestParam Integer quantity, @PathVariable Long id)
            throws JsonProcessingException {

        BirdShopItem shopItemToRemove=new BirdShopItem();
        Optional<Bird> birdGet = birdRepository.findById(id);
        shopItemToRemove.setQuantity(quantity);

        if(birdGet.isEmpty())return "redirect:/home";
        shopItemToRemove.setBirdProduct(birdGet.get());

        Cart cart=cookieCartUtil.getCookieCart(request);

        cart.removeItemWithQuantity(shopItemToRemove);
        //add new cookie
        String encodedCartString=cookieCartUtil.encodeCart(cart);
        Cookie cookie=new Cookie("cart",encodedCartString);
        cookie.setPath("/");
        //30min
        cookie.setMaxAge(60*30);
        response.addCookie(cookie);
        return "redirect:/";
    }
    @PostMapping("/removeBirdFromCart/{id}")
    public String removeBirdFromCart(HttpServletResponse response,HttpServletRequest request,@PathVariable Long id)
            throws JsonProcessingException {

        Optional<Bird> birdGet = birdRepository.findById(id);

        if(birdGet.isEmpty())return "redirect:/home";

        Cart cart=cookieCartUtil.getCookieCart(request);

        cart.removeItem(birdGet.get());
        //add new cookie
        String encodedCartString=cookieCartUtil.encodeCart(cart);
        Cookie cookie=new Cookie("cart",encodedCartString);
        cookie.setPath("/");
        //30min
        cookie.setMaxAge(60*30);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
