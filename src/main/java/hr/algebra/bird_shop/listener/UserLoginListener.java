package hr.algebra.bird_shop.listener;

import hr.algebra.bird_shop.domain.UserLoginEventInfo;
import hr.algebra.bird_shop.repository.UserLoginInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Component
public class UserLoginListener implements ApplicationListener {

    @Autowired
    private  UserLoginInfoRepository userLoginInfoRepository;



    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof AuthenticationSuccessEvent)
        {
            AuthenticationSuccessEvent authEvent = (AuthenticationSuccessEvent) event;
            String username=authEvent.getAuthentication().getName();
            String ipAddress=getIpAddress();
            System.out.println("Someone logged in:"+username+" and here is the ip:"+ipAddress);
            //Here would go the new class that would be saved
            UserLoginEventInfo userLoginEventInfo=new UserLoginEventInfo(username,ipAddress, LocalDateTime.now());
            userLoginInfoRepository.save(userLoginEventInfo);
        }
    }

    private String getIpAddress() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request.getRemoteAddr();
    }
}
