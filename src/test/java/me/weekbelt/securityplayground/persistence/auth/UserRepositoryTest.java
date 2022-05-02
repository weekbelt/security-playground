package me.weekbelt.securityplayground.persistence.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("")
    public void testcase() {
        // given
        User user = User.builder()
            .username("weekbelt")
            .password("test")
            .nickname("김주혁")
            .role(UserRole.ROLE_ADMIN)
            .build();

        User persistUser = entityManager.persist(user);

        // when
        User findUser = userRepository.findById(user.getId()).get();

        // then
        assertThat(findUser).isEqualTo(persistUser);
    }
}