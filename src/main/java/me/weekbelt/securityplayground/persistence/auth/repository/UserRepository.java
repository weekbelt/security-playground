package me.weekbelt.securityplayground.persistence.auth.repository;

import me.weekbelt.securityplayground.persistence.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
