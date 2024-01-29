package hr.algebra.bird_shop.repository;

import hr.algebra.bird_shop.domain.BirdUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<BirdUser,Long> {
    BirdUser findByUsername(String username);

}
