package me.weekbelt.securityplayground.persistence.auth.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import me.weekbelt.securityplayground.persistence.auth.UserRole;
import me.weekbelt.securityplayground.persistence.auth.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRoleDataService {

    private final UserRoleRepository userRoleRepository;

    public void saveAll(Set<UserRole> userRoles) {
        userRoleRepository.saveAll(userRoles);
    }
}
