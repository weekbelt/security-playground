package me.weekbelt.securityplayground.persistence.auth.repository;

import me.weekbelt.securityplayground.persistence.auth.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourcesRepository extends JpaRepository<Resources, Long> {
}
