package me.weekbelt.securityplayground.apiserver.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.transaction.Transactional;
import me.weekbelt.securityplayground.apiserver.auth.dto.UserSaveRequest;
import me.weekbelt.securityplayground.apiserver.auth.service.UserService;
import me.weekbelt.securityplayground.persistence.auth.Role;
import me.weekbelt.securityplayground.persistence.auth.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void initRole() {
        Role roleUser = Role.builder()
            .roleName("ROLE_USER")
            .build();
        roleRepository.save(roleUser);

        Role roleAdmin = Role.builder()
            .roleName("ROLE_ADMIN")
            .build();
        roleRepository.save(roleAdmin);
    }

    @Test
    @DisplayName("Role 생성")
    public void create_role_manager() throws Exception {
        // given
        String role = "ROLE_MANAGER";

        // when
        ResultActions resultActions = mockMvc.perform(post("/admin/v1/auth/roles")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(role));

        // then
        resultActions
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원가입")
    public void join_success() throws Exception {
        // given
        UserSaveRequest userSaveRequest = UserSaveRequest.builder()
            .username("weekbelt")
            .name("김주혁")
            .password("1234")
            .roles(List.of("ROLE_USER", "ROLE_ADMIN"))
            .build();
        System.out.println(objectMapper.writeValueAsString(userSaveRequest));

        // when
        ResultActions resultActions = mockMvc.perform(post("/admin/v1/auth/users")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(userSaveRequest)));

        // then
        resultActions
            .andDo(print())
            .andExpect(status().isCreated())
        ;
    }

    @Test
    @DisplayName("전체 회원 리스트 호출")
    public void get_all_users() throws Exception {
        // given
        UserSaveRequest userSaveRequest = UserSaveRequest.builder()
            .username("weekbelt")
            .name("김주혁")
            .password("1234")
            .roles(List.of("ROLE_USER", "ROLE_ADMIN"))
            .build();
        userService.save(userSaveRequest);

        // when
        ResultActions resultActions = mockMvc.perform(get("/admin/v1/auth/users"));

        // then
        resultActions
            .andDo(print())
            .andExpect(status().isOk())
        ;
    }
}