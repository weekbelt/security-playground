package me.weekbelt.securityplayground.apiserver.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class RoleResponse {

    private Long id;

    private String role;
}
