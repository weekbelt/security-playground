package me.weekbelt.securityplayground.persistence.auth;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDataService {

    private final UserRepository userRepository;
}
