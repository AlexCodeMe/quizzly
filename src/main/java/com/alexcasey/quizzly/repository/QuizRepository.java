package com.alexcasey.quizzly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexcasey.quizzly.model.Quiz;
import com.alexcasey.quizzly.model.User;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByUser(User user);
}
