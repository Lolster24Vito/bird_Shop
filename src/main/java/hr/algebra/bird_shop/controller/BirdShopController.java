package hr.algebra.bird_shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.algebra.bird_shop.domain.Bird;
import hr.algebra.bird_shop.domain.BirdShopItem;
import hr.algebra.bird_shop.domain.BirdTag;
import hr.algebra.bird_shop.domain.Cart;
import hr.algebra.bird_shop.repository.BirdRepository;
import hr.algebra.bird_shop.repository.BirdTagRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
//todo add
//@SessionAttributes({"allFoodList", "newFood"})
public class BirdShopController {

    private final ObjectMapper objectMapper=new ObjectMapper();

    private final BirdRepository birdRepository;
    private final BirdTagRepository birdTagRepository;

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
        model.addAttribute("cookieCart", getCookieCart(request));

    }


    /*todo add ....on investigation dont add
        @ModelAttribute("allFoodList")
    public List<Food> storeAllFood() {
        return foodRepository.getAllFood();
    }

    @ModelAttribute("newFood")
    public Food storeEmptyObject() {
        return new Food();
    }
     */
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


    @GetMapping("addTags")
    public String addTags(Model model){
        model.addAttribute("birdTags",birdTagRepository.findAll());
        model.addAttribute("createTag",new BirdTag());
        return "addTags";
    }
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
            throws UnsupportedEncodingException, JsonProcessingException {
        //code to save check if cookie is here,if yes add bird to cookie with quantity

                /* code to delete cookie
        Cookie cooki=new Cookie("cart",null);
        cooki.setMaxAge(0);
        response.addCookie(cooki);

        return "redirect:/home";
        */


        BirdShopItem shopItemToAdd=new BirdShopItem();
        Optional<Bird> birdGet = birdRepository.findById(id);
        shopItemToAdd.setQuantity(quantity);

        if(birdGet.isEmpty())return "redirect:/home";
        shopItemToAdd.setBirdProduct(birdGet.get());

        Cart cart=getCookieCart(request);

        cart.addItem(shopItemToAdd);
            //add new cookie


        String encodedCartString=encodeCart(cart);
            //todo add new cart with new birdProduct
            Cookie cookie=new Cookie("cart",encodedCartString);
            cookie.setPath("/");
        //30min
             cookie.setMaxAge(60*30);
            response.addCookie(cookie);
        System.out.println("YOU ARE HERE IN THE ADD BIRD your: "+id.toString()+"  and this much:"+quantity);
        return "redirect:/";
    }
    @PostMapping("/removeBirdFromCartQuantity/{id}")
    public String removeBirdFromCartQuantity(HttpServletResponse response,HttpServletRequest request,
                                             @RequestParam Integer quantity, @PathVariable Long id)
            throws UnsupportedEncodingException, JsonProcessingException {

        BirdShopItem shopItemToRemove=new BirdShopItem();
        Optional<Bird> birdGet = birdRepository.findById(id);
        shopItemToRemove.setQuantity(quantity);

        if(birdGet.isEmpty())return "redirect:/home";
        shopItemToRemove.setBirdProduct(birdGet.get());

        Cart cart=getCookieCart(request);

        cart.removeItemWithQuantity(shopItemToRemove);
        //add new cookie
        String encodedCartString=encodeCart(cart);
        Cookie cookie=new Cookie("cart",encodedCartString);
        cookie.setPath("/");
        //30min
        cookie.setMaxAge(60*30);
        response.addCookie(cookie);
        return "redirect:/";
    }
    @PostMapping("/removeBirdFromCart/{id}")
    public String removeBirdFromCart(HttpServletResponse response,HttpServletRequest request,@PathVariable Long id)
            throws UnsupportedEncodingException, JsonProcessingException {

        Optional<Bird> birdGet = birdRepository.findById(id);

        if(birdGet.isEmpty())return "redirect:/home";

        Cart cart=getCookieCart(request);

        cart.removeItem(birdGet.get());
        //add new cookie
        String encodedCartString=encodeCart(cart);
        Cookie cookie=new Cookie("cart",encodedCartString);
        cookie.setPath("/");
        //30min
        cookie.setMaxAge(60*30);
        response.addCookie(cookie);
        return "redirect:/";
    }

    private Cart getCookieCart(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Cart cart=new Cart();

        if (cookies != null && isCartInCookies(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")) {
                    String oldCartStringValue = cookie.getValue();
                    String decodedJSON= URLDecoder.decode(oldCartStringValue, StandardCharsets.UTF_8);

                    try {
                        cart = objectMapper.readValue(decodedJSON, Cart.class);
                    } catch (JsonProcessingException e) {
                        System.out.println("cannot parse json:"+decodedJSON);
                        e.printStackTrace();
                    }
                }
            }

        }
        return cart;
    }
    private String encodeCart(Cart cart) throws JsonProcessingException {
        //toJSON
        String cartString = objectMapper.writeValueAsString(cart);
        //to UTF8 because of json invalid characters
        String encodedCartString=URLEncoder.encode(cartString, StandardCharsets.UTF_8);
        return  encodedCartString;
    }


    private boolean isCartInCookies(Cookie[] cookies) {
        for (Cookie cook: cookies) {
            if(cook.getName().equals("cart")){
                return true;
            }

        }
        return false;
    }


}
