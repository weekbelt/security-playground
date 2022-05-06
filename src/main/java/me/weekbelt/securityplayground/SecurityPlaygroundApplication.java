package me.weekbelt.securityplayground;

import java.util.List;
import me.weekbelt.securityplayground.apiserver.auth.dto.MemberSaveRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SecurityPlaygroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityPlaygroundApplication.class, args);
    }

    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.save("ROLE_USER");
            userService.save("ROLE_MANAGER");

            MemberSaveRequest adminMemberSaveRequest = MemberSaveRequest.builder()
                .username("weekbelt")
                .name("김주혁")
                .roles(List.of("ROLE_USER", "ROLE_ADMIN"))
                .build();
            userService.save(adminMemberSaveRequest);
            MemberSaveRequest userMemberSaveRequest = MemberSaveRequest.builder()
                .username("vfrvfr4207")
                .name("김현수")
                .roles(List.of("ROLE_USER"))
                .build();
            userService.save(userMemberSaveRequest);
        };
    }
}
