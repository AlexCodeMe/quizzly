package com.alexcasey.quizzly.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alexcasey.quizzly.enums.RoleEnum;
import com.alexcasey.quizzly.model.Role;
import com.alexcasey.quizzly.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        initializeRoles();
    }

    private void initializeRoles() {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (!roleRepository.existsByRole(roleEnum)) {
                Role role = new Role(roleEnum);
                roleRepository.save(role);
                log.info("Inserted role: {}", roleEnum.name());
            } else {
                log.info("Role already exists: {}", roleEnum.name());
            }
        }
    }
}
