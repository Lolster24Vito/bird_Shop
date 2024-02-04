package hr.algebra.bird_shop.repository;

import hr.algebra.bird_shop.domain.BirdOrder;
import org.springframework.data.repository.CrudRepository;

public interface BirdOrderRepository extends CrudRepository<BirdOrder,Long> {
}
