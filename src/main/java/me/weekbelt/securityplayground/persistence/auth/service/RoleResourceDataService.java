package me.weekbelt.securityplayground.persistence.auth.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.securityplayground.persistence.auth.repository.RoleResourcesRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleResourceDataService {

    private final RoleResourcesRepository roleResourcesRepository;
}
