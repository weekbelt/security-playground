package me.weekbelt.securityplayground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SecurityPlaygroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityPlaygroundApplication.class, args);
    }

}
