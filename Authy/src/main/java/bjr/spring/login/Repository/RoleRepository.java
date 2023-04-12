package bjr.spring.login.Repository;

import java.util.Optional;

import bjr.spring.login.Entity.UserRole;
import bjr.spring.login.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(UserRole name);
}
