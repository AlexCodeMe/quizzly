package com.alexcasey.quizzly.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alexcasey.quizzly.enums.RoleEnum;
import com.alexcasey.quizzly.model.Role;
import com.alexcasey.quizzly.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final RoleRepository roleRepository;

    // Constructor instead of @RequiredArgsConstructor
    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

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