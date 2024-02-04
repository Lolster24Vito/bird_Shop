package hr.algebra.bird_shop.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hr.algebra.bird_shop.domain.Cart;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CookieCartUtil {
    private final ObjectMapper objectMapper;

    public CookieCartUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public Cart getCookieCart(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Cart cart=new Cart();

        if (cookies != null && isCartInCookies(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")&&cookie.getValue()!=null&&!cookie.getValue().isEmpty()) {
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
    public void deleteCartCookies(HttpServletResponse response){
        Cookie cookie=new Cookie("cart",null);
        cookie.setPath("/");
        cookie.setMaxAge(60*30);
        response.addCookie(cookie);
    }
    private boolean isCartInCookies(Cookie[] cookies) {
        for (Cookie cook: cookies) {
            if(cook.getName().equals("cart")){
                return true;
            }

        }
        return false;
    }
    public String encodeCart(Cart cart) throws JsonProcessingException {
        //toJSON
        String cartString = objectMapper.writeValueAsString(cart);
        //to UTF8 because of json invalid characters
        String encodedCartString= URLEncoder.encode(cartString, StandardCharsets.UTF_8);
        return  encodedCartString;
    }
}
