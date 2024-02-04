package hr.algebra.bird_shop.repository;

import hr.algebra.bird_shop.domain.BirdOrder;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface BirdOrderRepository extends CrudRepository<BirdOrder,Long> {

    Iterable<BirdOrder> findAllByBirdUser_UsernameIgnoreCaseContaining(String username);

    Iterable<BirdOrder> findAllByCreatedTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
