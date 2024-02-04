package hr.algebra.bird_shop.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/adminPage").hasRole("ADMIN")
                        .requestMatchers("/userPage").hasAnyRole("USER","ADMIN")
                        .requestMatchers(toH2Console()).permitAll()
                        .anyRequest().permitAll()

                ).formLogin(formLogin->
                        formLogin.loginPage("/login"))
                .logout(logout ->
                        logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                )
                //this whole line and .requestMatchers(toH2Console() is purely  for opening h2Console because by default it's blocked
               //.csrf(csrf->csrf.disable()).headers(headers->headers.frameOptions(frameOptions->frameOptions.disable()))
                ;

        return http.build();
    }

   /* @Bean
    public UserDetailsService userDetailsService(){


    }*/
//    login html X
//  register html X
// config with site names antmatchers X
    //User repo
    /*
     .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll();



  UserDetailsService
  password encoder
  User class

     */
}
