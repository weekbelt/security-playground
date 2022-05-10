package me.weekbelt.securityplayground.persistence.auth.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.securityplayground.persistence.auth.repository.ResourcesRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ResourcesDataService {

    private final ResourcesRepository resourcesRepository;
}
