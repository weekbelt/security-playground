package me.weekbelt.securityplayground.persistence.auth.mapper;

import java.util.List;
import java.util.stream.Collectors;
import me.weekbelt.securityplayground.apiserver.auth.dto.UserResponse;
import me.weekbelt.securityplayground.apiserver.auth.dto.UserSaveRequest;
import me.weekbelt.securityplayground.persistence.auth.Role;
import me.weekbelt.securityplayground.persistence.auth.User;
import me.weekbelt.securityplayground.persistence.auth.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    public static User toUser(UserSaveRequest userSaveRequest, PasswordEncoder passwordEncoder) {
        return User.builder()
            .username(userSaveRequest.getUsername())
            .password(passwordEncoder.encode(userSaveRequest.getPassword()))
            .name(userSaveRequest.getName())
            .build();
    }

    public static UserResponse toUserResponseDto(User user) {
        List<String> userRoles = user.getUserRoles().stream()
            .map(userRole -> userRole.getRole().getRoleName())
            .collect(Collectors.toList());

        return UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .name(user.getName())
            .userRoles(userRoles)
            .build();
    }

    public static UserRole toUserRole(User user, Role role) {
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        return userRole;
    }
}
