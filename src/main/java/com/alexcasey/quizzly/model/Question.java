package com.alexcasey.quizzly.model;

import java.util.Set;

import com.alexcasey.quizzly.enums.QuestionTypeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String question;
    String answer;
    Set<String> options;
    String explanation;
    @Enumerated(EnumType.STRING)
    QuestionTypeEnum questionType;

    @ManyToMany(mappedBy = "questions")
    Set<Quiz> quizzes;
}
