package com.alexcasey.quizzly.service;

import java.util.Collections;
import java.util.Optional;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AccountService(PasswordEncoder passwordEncoder, AccountRepository accountRepository,
            UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Account createAccount(AuthRequest request) {
        log.debug("Attempting to create account for username: {}", request.username());

        if (accountRepository.findByUsername(request.username()).isPresent()) {
            log.warn("Username already exists: {}", request.username());
            throw new UserAlreadyExistsException("Username already exists");
        }

        Account newAccount = new Account();
        newAccount.setUsername(request.username());
        newAccount.setPassword(passwordEncoder.encode(request.password()));

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
