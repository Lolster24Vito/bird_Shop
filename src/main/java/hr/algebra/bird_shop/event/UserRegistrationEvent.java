package hr.algebra.bird_shop.event;

import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {
    private String username;

    public UserRegistrationEvent(Object source,String username) {
        super(source);
        this.username=username;
    }

    public String getUsername() {
        return username;
    }
}
