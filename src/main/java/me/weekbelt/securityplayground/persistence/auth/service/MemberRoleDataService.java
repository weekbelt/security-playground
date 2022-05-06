package me.weekbelt.securityplayground.persistence.auth.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import me.weekbelt.securityplayground.persistence.auth.MemberRole;
import me.weekbelt.securityplayground.persistence.auth.repository.MemberRoleRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberRoleDataService {

    private final MemberRoleRepository memberRoleRepository;

    public void saveAll(Set<MemberRole> memberRoles) {
        memberRoleRepository.saveAll(memberRoles);
    }
}

