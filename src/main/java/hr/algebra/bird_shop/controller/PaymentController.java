package hr.algebra.bird_shop.controller;

import hr.algebra.bird_shop.domain.BirdOrder;
import hr.algebra.bird_shop.domain.BirdUser;
import hr.algebra.bird_shop.domain.Cart;
import hr.algebra.bird_shop.domain.OrderPayType;
import hr.algebra.bird_shop.repository.BirdOrderRepository;
import hr.algebra.bird_shop.repository.UserRepository;
import hr.algebra.bird_shop.util.CookieCartUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
@Controller
@AllArgsConstructor
public class PaymentController {
    @Autowired
    private UserRepository userRepository;
    private final CookieCartUtil cookieCartUtil;
    private final BirdOrderRepository birdOrderRepository;

    @GetMapping("/payment")
    public String paymentPage(Model model, HttpServletRequest request){
        Cart cart=cookieCartUtil.getCookieCart(request);
        BigDecimal total = cart.getTotal();
        model.addAttribute("totalSum",cart.getTotal().toString());
        model.addAttribute("cart",cart);
        return "payment";
    }
    @GetMapping("/payment/paymentSuccess")
    public String handleSuccess(HttpServletRequest request,HttpServletResponse response) {
        saveOrder(request, OrderPayType.PAYPAL);
        cookieCartUtil.deleteCartCookies(response);
        return "order/success"; // Thymeleaf template name for success page
    }
    @GetMapping("/payment/paymentSuccessCash")
    public String handleSuccessCash(HttpServletRequest request, HttpServletResponse response) {
        saveOrder(request, OrderPayType.CASH);
        cookieCartUtil.deleteCartCookies(response);
        return "order/success"; // Thymeleaf template name for success page
    }
@GetMapping("/payment/paymentSuccessCashDelivery")
public String handleSuccessCashDelivery(HttpServletRequest request, HttpServletResponse response) {
    saveOrder(request, OrderPayType.CASH_ON_DELIVERY);
    cookieCartUtil.deleteCartCookies(response);
    return "order/success"; // Thymeleaf template name for success page
}

    private void saveOrder(HttpServletRequest request, OrderPayType orderPayType) {
        Cart cart = cookieCartUtil.getCookieCart(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BirdUser birdUser = userRepository.findByUsername(authentication.getName());
        BirdOrder birdOrder = new BirdOrder(cart.getItems(),cart.getTotal(), LocalDateTime.now(), orderPayType,
                birdUser, birdUser.getShippingAddress());
        birdOrderRepository.save(birdOrder);
    }

    @GetMapping("/payment/cancelMessage")
    public String handleCancel() {
        // Logic for a canceled payment
        return "order/cancel"; // Thymeleaf template name for cancel page
    }
    @GetMapping("/payment/errorMessage")
    public String handleErrorMessage() {
        // Logic for a canceled payment
        return "order/orderError"; // Thymeleaf template name for cancel page
    }


}
