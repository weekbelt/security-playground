package me.weekbelt.securityplayground.apiserver.auth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import javax.transaction.Transactional;
import me.weekbelt.securityplayground.apiserver.auth.dto.MemberSaveRequest;
import me.weekbelt.securityplayground.apiserver.auth.service.MemberService;
import me.weekbelt.securityplayground.persistence.auth.entity.Role;
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
public class MemberControllerTest {

    private static final String BASE_PATH = "/admin/v1/auth";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MemberService memberService;

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
    @DisplayName("Role ??????")
    public void create_role_manager() throws Exception {
        // given
        String role = "ROLE_MANAGER";

        // when
        ResultActions resultActions = mockMvc.perform(post(BASE_PATH + "/roles")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(role));

        // then
        resultActions
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("????????????")
    public void join_success() throws Exception {
        // given
        MemberSaveRequest memberSaveRequest = MemberSaveRequest.builder()
            .username("weekbelt")
            .name("?????????")
            .password("1234")
            .roles(List.of("ROLE_USER", "ROLE_ADMIN"))
            .build();
        System.out.println(objectMapper.writeValueAsString(memberSaveRequest));

        // when
        ResultActions resultActions = mockMvc.perform(post(BASE_PATH + "/members")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(memberSaveRequest)));

        // then
        resultActions
            .andDo(print())
            .andExpect(status().isCreated())
        ;
    }

    @Test
    @DisplayName("?????? ?????? ????????? ??????")
    public void get_all_users() throws Exception {
        // given
        MemberSaveRequest memberSaveRequest = MemberSaveRequest.builder()
            .username("weekbelt")
            .name("?????????")
            .password("1234")
            .roles(List.of("ROLE_USER", "ROLE_ADMIN"))
            .build();
        memberService.save(memberSaveRequest);

        // when
        ResultActions resultActions = mockMvc.perform(get(BASE_PATH + "/members"));

        // then
        resultActions
            .andDo(print())
            .andExpect(status().isOk())
        ;
    }
}