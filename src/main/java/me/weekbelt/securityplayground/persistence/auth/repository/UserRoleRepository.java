package me.weekbelt.securityplayground.persistence.auth.repository;

import me.weekbelt.securityplayground.persistence.auth.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
