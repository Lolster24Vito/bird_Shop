package hr.algebra.bird_shop.repository;

import hr.algebra.bird_shop.domain.UserLoginEventInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserLoginInfoRepository extends CrudRepository<UserLoginEventInfo,Long> {
}
