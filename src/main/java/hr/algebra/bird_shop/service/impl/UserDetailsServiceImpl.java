package hr.algebra.bird_shop.service.impl;

import hr.algebra.bird_shop.domain.BirdUser;
import hr.algebra.bird_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public BirdUser loadUserByUsername(String username) throws UsernameNotFoundException {
        BirdUser birdUser = userRepository.findByUsername(username);
        if(birdUser ==null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return birdUser;
    }
}
