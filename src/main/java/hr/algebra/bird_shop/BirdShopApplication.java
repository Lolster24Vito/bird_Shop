package hr.algebra.bird_shop;

import hr.algebra.bird_shop.domain.Bird;
import hr.algebra.bird_shop.domain.BirdTag;
import hr.algebra.bird_shop.domain.BirdUser;
import hr.algebra.bird_shop.domain.Role;
import hr.algebra.bird_shop.repository.BirdRepository;
import hr.algebra.bird_shop.repository.BirdTagRepository;
import hr.algebra.bird_shop.repository.RoleRepository;
import hr.algebra.bird_shop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class BirdShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(BirdShopApplication.class, args);
    }

    @Bean
    CommandLineRunner dataLoader(BirdRepository birdRepo, BirdTagRepository birdTagRepo, UserRepository userRepo,
                                 RoleRepository roleRepo, PasswordEncoder passwordEncoder){
        return args -> {
            //To ensure that BirdTags are also inserted along with Birds I'm creating BirdTagsFirst
            BirdTag tag1 = birdTagRepo.save(new BirdTag("sharp talons"));
            BirdTag tag2 = birdTagRepo.save(new BirdTag("not sharp talons"));
            BirdTag tag3 = birdTagRepo.save(new BirdTag("feathers"));
            BirdTag tag4 = birdTagRepo.save(new BirdTag("Low Noise Level"));
            BirdTag tag5 = birdTagRepo.save(new BirdTag("High Noise Level"));


            birdRepo.save(new Bird("diid","didles around",false, BigDecimal.valueOf(2.99),
             Arrays.asList(tag1,tag2)));
            birdRepo.save(new Bird("dodo","dodos around",false,BigDecimal.valueOf(5.99),
            Arrays.asList(tag4,tag5)));
            birdRepo.save(new Bird("birdo","birdos around",true,BigDecimal.valueOf(4.99),
                    Arrays.asList(tag3,tag2)));
            birdRepo.save(new Bird("Ptica","pticos around",true,BigDecimal.valueOf(4.99),
                    Arrays.asList(tag1,tag3)));

            Role roleAdmin=roleRepo.save(new Role("ROLE_ADMIN"));
            Role roleUser=roleRepo.save(new Role("ROLE_USER"));



            BirdUser birdUser=new BirdUser("s",passwordEncoder.encode("d"),"f",
                    "s@gmail.com",new HashSet<>(Arrays.asList(roleUser ))     );
            BirdUser birdUser1=new BirdUser("f",passwordEncoder.encode("d"),"s",
                    "f@gmail.com",new HashSet<>(Arrays.asList(roleUser)) );

            BirdUser birdUser2=new BirdUser("admin",passwordEncoder.encode("admin"),"I am mr admin",
                    "admin@gmail.com",new HashSet<>(Arrays.asList(roleAdmin)) );
            userRepo.save(birdUser);
            userRepo.save(birdUser1);
            userRepo.save(birdUser2);

            Iterable<BirdUser> all = userRepo.findAll();
            System.out.println("yup im here");


        };
    }
}
