package me.weekbelt.securityplayground.persistence.auth.repository;

import java.util.Optional;
import me.weekbelt.securityplayground.persistence.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
}
