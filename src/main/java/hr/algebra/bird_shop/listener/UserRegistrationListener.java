package hr.algebra.bird_shop.listener;

import hr.algebra.bird_shop.event.UserRegistrationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationListener implements ApplicationListener<UserRegistrationEvent> {
    @Async
    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        System.out.println("Received spring  event user registration for: " + event.getUsername());
    }
}
