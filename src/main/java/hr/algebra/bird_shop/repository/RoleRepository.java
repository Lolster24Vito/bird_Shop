package hr.algebra.bird_shop.repository;

import hr.algebra.bird_shop.domain.BirdUser;
import hr.algebra.bird_shop.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByNameEquals(String name);
}
