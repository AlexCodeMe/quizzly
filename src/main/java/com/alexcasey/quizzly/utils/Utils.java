package com.alexcasey.quizzly.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.alexcasey.quizzly.security.user.AccountDetails;
import com.alexcasey.quizzly.exception.UserNotFoundException;
import com.alexcasey.quizzly.model.User;
import com.alexcasey.quizzly.repository.UserRepository;

@Component
public class Utils {

    private final UserRepository userRepository;

    public Utils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal(); // Cast to AccountDetails

        // Fetch the User entity based on accountDetails (use the id or username)
        Long accountId = accountDetails.getId();
        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UserNotFoundException("User with account id [" + accountId + "] not found"));

        return user;
    }
}
