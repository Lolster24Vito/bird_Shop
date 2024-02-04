package hr.algebra.bird_shop.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Component
@Order(2)
public class RequestResponseFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        System.out.println(
                "Logging Request  "+ req.getMethod()+" : "+
                req.getRequestURI());
        chain.doFilter(request, response);
        System.out.println(
                "Logging Response :"+
                res.getContentType());
    }

    // other methods
}