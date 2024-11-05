package com.alexcasey.quizzly.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String description;
    String topic;

    @ManyToOne
    User user;

    @ManyToMany
    @JoinTable(name = "quizzes_questions", joinColumns = @JoinColumn(name = "quiz_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "quiz")
    private Set<QuizResult> quizResults = new HashSet<>();

    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    // no args constructor
    public Quiz() {
    }

    // all args constructor
    public Quiz(Long id, String name, String description, String topic, User user, Set<Question> questions,
            Set<QuizResult> quizResults, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.topic = topic;
        this.user = user;
        this.questions = questions;
        this.quizResults = quizResults;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTopic() {
        return topic;
    }

    public User getUser() {
        return user;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Set<QuizResult> getQuizResults() {
        return quizResults;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions != null ? questions : new HashSet<>();
    }

    public void setQuizResults(Set<QuizResult> quizResults) {
        this.quizResults = quizResults != null ? quizResults : new HashSet<>();
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quiz)) {
            return false;
        }
        Quiz quiz = (Quiz) o;
        return id != null && id.equals(quiz.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
