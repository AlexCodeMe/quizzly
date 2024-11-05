package com.alexcasey.quizzly.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    Account account;

    @OneToMany(mappedBy = "user")
    private Set<Quiz> quizzes = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private Set<QuizResult> quizResults = new HashSet<>();

    // no args constructor
    public User() {}

    // all args constructor
    public User(Long id, Account account, Set<Quiz> quizzes, Set<QuizResult> quizResults) {
        this.id = id;
        this.account = account;
        this.quizzes = quizzes != null ? quizzes : new HashSet<>();
        this.quizResults = quizResults != null ? quizResults : new HashSet<>();
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public Set<QuizResult> getQuizResults() {
        return quizResults;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes != null ? quizzes : new HashSet<>();
    }

    public void setQuizResults(Set<QuizResult> quizResults) {
        this.quizResults = quizResults != null ? quizResults : new HashSet<>();
    }

    // equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id != null && id.equals(user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
