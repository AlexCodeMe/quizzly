package com.alexcasey.quizzly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alexcasey.quizzly.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccountId(Long accountId);
}
