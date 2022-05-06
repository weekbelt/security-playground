package me.weekbelt.securityplayground.apiserver.auth.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.weekbelt.securityplayground.apiserver.auth.dto.RoleResponse;
import me.weekbelt.securityplayground.apiserver.auth.dto.UserResponse;
import me.weekbelt.securityplayground.apiserver.auth.dto.UserSaveRequest;
import me.weekbelt.securityplayground.apiserver.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    @PostMapping("/v1/auth/roles")
    @ResponseStatus(value = HttpStatus.CREATED)
    public RoleResponse createRole(@RequestBody String role) {
        return userService.save(role);
    }

    @PostMapping("/v1/auth/users")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserResponse join(@RequestBody UserSaveRequest userSaveRequest) {
        return userService.save(userSaveRequest);
    }

    @GetMapping("/v1/auth/users")
    public ResponseEntity<List<UserResponse>> getUsers() {
        List<UserResponse> userResponses = userService.getUsers();
        return ResponseEntity.ok().body(userResponses);
    }
}
