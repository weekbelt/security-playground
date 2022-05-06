package me.weekbelt.securityplayground.persistence.auth.repository;

import java.util.Optional;
import me.weekbelt.securityplayground.persistence.auth.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> getByUsername(String username);
}
