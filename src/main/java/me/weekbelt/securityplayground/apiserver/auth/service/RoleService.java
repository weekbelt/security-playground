package me.weekbelt.securityplayground.apiserver.auth.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.securityplayground.apiserver.auth.dto.RoleResponse;
import me.weekbelt.securityplayground.persistence.auth.Role;
import me.weekbelt.securityplayground.persistence.auth.mapper.RoleMapper;
import me.weekbelt.securityplayground.persistence.auth.service.RoleDataService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleDataService roleDataService;

    public RoleResponse save(String roleName) {
        Role role = Role.builder()
            .roleName(roleName)
            .build();
        Role savedRole = roleDataService.save(role);
        return RoleMapper.toRoleResponse(savedRole);
    }
}
