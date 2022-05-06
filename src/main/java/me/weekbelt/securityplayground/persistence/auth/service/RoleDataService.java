package me.weekbelt.securityplayground.persistence.auth.service;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import me.weekbelt.securityplayground.persistence.auth.Role;
import me.weekbelt.securityplayground.persistence.auth.repository.RoleRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleDataService {

    private final RoleRepository roleRepository;

    public Role getByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName)
            .orElseThrow(() -> new EntityNotFoundException("Cannot find roleName : " + roleName));
    }
}
