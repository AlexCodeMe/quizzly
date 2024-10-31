package com.alexcasey.quizzly.service;

import java.util.Collections;
import java.util.Optional;
import java.util.HashSet;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexcasey.quizzly.enums.RoleEnum;
import com.alexcasey.quizzly.exception.RoleNotFoundException;
import com.alexcasey.quizzly.exception.UserAlreadyExistsException;
import com.alexcasey.quizzly.model.Account;
import com.alexcasey.quizzly.model.Role;
import com.alexcasey.quizzly.repository.AccountRepository;
import com.alexcasey.quizzly.repository.RoleRepository;
import com.alexcasey.quizzly.repository.UserRepository;
import com.alexcasey.quizzly.request.AuthRequest;
import com.alexcasey.quizzly.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public Account createAccount(AuthRequest request) {
        log.debug("Attempting to create account for username: {}", request.getUsername());

        if (accountRepository.findByUsername(request.getUsername()).isPresent()) {
            log.warn("Username already exists: {}", request.getUsername());
            throw new UserAlreadyExistsException("Username already exists");
        }

        Account newAccount = new Account();
        newAccount.setUsername(request.getUsername());
        newAccount.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findByRole(RoleEnum.USER)
                .orElseThrow(() -> new RoleNotFoundException("User role not found"));
        newAccount.setRoles(Collections.singleton(userRole));
        
        
        User newUser = new User();
        newUser.setAccount(newAccount);
        newUser.setQuizzes(new HashSet<>());
        newUser.setQuizResults(new HashSet<>());
        userRepository.save(newUser);

        newAccount.setUser(newUser);
        Account savedAccount = accountRepository.save(newAccount);

        log.info("Successfully created account for username: {}", savedAccount.getUsername());
        return savedAccount;
    }
}
