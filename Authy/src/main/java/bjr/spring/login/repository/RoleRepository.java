package bjr.spring.login.repository;

import java.util.Optional;

import bjr.spring.login.models.ERole;
import bjr.spring.login.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
