package com.alexcasey.quizzly.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "quiz_results")
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    double score;

    LocalDateTime completedAt;

    @ManyToOne
    User user;

    @ManyToOne
    Quiz quiz;

    // no args constructor
    public QuizResult() {}

    // all args constructor
    public QuizResult(Long id, double score, LocalDateTime completedAt, User user, Quiz quiz) {
        this.id = id;
        this.score = score;
        this.completedAt = completedAt;
        this.user = user;
        this.quiz = quiz;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public double getScore() {
        return score;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public User getUser() {
        return user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    // equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof QuizResult))
            return false;
        QuizResult quizResult = (QuizResult) o;
        return id != null && id.equals(quizResult.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
