package me.weekbelt.securityplayground.apiserver.auth.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String username;

    private String name;

    private List<String> userRoles;
}
