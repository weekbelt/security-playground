package me.weekbelt.securityplayground.persistence.auth.mapper;

import me.weekbelt.securityplayground.apiserver.auth.dto.RoleResponse;
import me.weekbelt.securityplayground.persistence.auth.Role;

public class RoleMapper {

    public static RoleResponse toRoleResponse(Role role) {
        return RoleResponse.builder()
            .id(role.getId())
            .role(role.getRoleName())
            .build();
    }
}
