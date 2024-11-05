package com.alexcasey.quizzly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexcasey.quizzly.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
