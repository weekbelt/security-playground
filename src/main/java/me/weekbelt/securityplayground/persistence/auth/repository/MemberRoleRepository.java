package me.weekbelt.securityplayground.persistence.auth.repository;

import me.weekbelt.securityplayground.persistence.auth.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
}
