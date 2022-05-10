package me.weekbelt.securityplayground.persistence.auth.repository;

import me.weekbelt.securityplayground.persistence.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleResourcesRepository extends JpaRepository<Role, Long> {
}
