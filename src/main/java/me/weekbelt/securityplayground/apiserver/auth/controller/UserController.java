package me.weekbelt.securityplayground.apiserver.auth.controller;

import lombok.RequiredArgsConstructor;
import me.weekbelt.securityplayground.apiserver.auth.dto.UserResponse;
import me.weekbelt.securityplayground.apiserver.auth.dto.UserSaveRequest;
import me.weekbelt.securityplayground.apiserver.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/v1/auth/users")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserResponse join(@RequestBody UserSaveRequest userSaveRequest) {
        return userService.save(userSaveRequest);
    }
}
