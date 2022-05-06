package me.weekbelt.securityplayground.apiserver.auth.service;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.weekbelt.securityplayground.apiserver.auth.dto.UserResponse;
import me.weekbelt.securityplayground.apiserver.auth.dto.UserSaveRequest;
import me.weekbelt.securityplayground.persistence.auth.Role;
import me.weekbelt.securityplayground.persistence.auth.User;
import me.weekbelt.securityplayground.persistence.auth.UserRole;
import me.weekbelt.securityplayground.persistence.auth.mapper.UserMapper;
import me.weekbelt.securityplayground.persistence.auth.service.RoleDataService;
import me.weekbelt.securityplayground.persistence.auth.service.UserDataService;
import me.weekbelt.securityplayground.persistence.auth.service.UserRoleDataService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserDataService userDataService;

    private final UserRoleDataService userRoleDataService;

    private final RoleDataService roleDataService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse save(UserSaveRequest userSaveRequest) {
        User user = createUser(userSaveRequest);
        User savedUser = userDataService.save(user);

        Set<UserRole> userRoles = createUserRoles(userSaveRequest, savedUser);
        userRoleDataService.saveAll(userRoles);

        return UserMapper.toUserResponseDto(savedUser);
    }

    private User createUser(UserSaveRequest userSaveRequest) {
        return UserMapper.toUser(userSaveRequest, passwordEncoder);
    }

    private Set<UserRole> createUserRoles(UserSaveRequest userSaveRequest, User user) {
        List<String> roles = userSaveRequest.getRoles();
        return roles.stream()
            .map(roleName -> {
                Role role = roleDataService.getByRoleName(roleName);
                return UserMapper.toUserRole(user, role);
            }).collect(toSet());
    }
}
