package hr.algebra.bird_shop;

import hr.algebra.bird_shop.domain.Bird;
import hr.algebra.bird_shop.domain.BirdTag;
import hr.algebra.bird_shop.repository.BirdRepository;
import hr.algebra.bird_shop.repository.BirdTagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class BirdShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(BirdShopApplication.class, args);
    }

    @Bean
    CommandLineRunner dataLoader(BirdRepository birdRepo, BirdTagRepository birdTagRepo){
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


        };
    }
}
