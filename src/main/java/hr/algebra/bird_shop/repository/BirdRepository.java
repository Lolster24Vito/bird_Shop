package hr.algebra.bird_shop.repository;

import hr.algebra.bird_shop.domain.Bird;
import org.springframework.data.repository.CrudRepository;

public interface BirdRepository extends CrudRepository<Bird,Long> {

}
