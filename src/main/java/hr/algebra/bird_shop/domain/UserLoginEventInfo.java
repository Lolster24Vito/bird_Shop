package hr.algebra.bird_shop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserLoginEventInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String ipAddress;
    private LocalDateTime loginTime;

    public UserLoginEventInfo(String username, String ipAddress, LocalDateTime loginTime) {
        this.username = username;
        this.ipAddress = ipAddress;
        this.loginTime = loginTime;
    }
}
