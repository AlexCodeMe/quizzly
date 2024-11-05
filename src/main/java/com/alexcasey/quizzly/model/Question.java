package com.alexcasey.quizzly.model;

import java.util.HashSet;
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

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String question;
    String answer;
    private Set<String> options = new HashSet<>();
    String explanation;
    @Enumerated(EnumType.STRING)
    QuestionTypeEnum questionType;

    @ManyToMany(mappedBy = "questions")
    private Set<Quiz> quizzes = new HashSet<>();

    // no args constructor
    public Question() {
    }

    // all args constructor
    public Question(Long id, String question, String answer, Set<String> options,
            String explanation, QuestionTypeEnum questionType, Set<Quiz> quizzes) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.options = options;
        this.explanation = explanation;
        this.questionType = questionType;
        this.quizzes = quizzes != null ? quizzes : new HashSet<>();
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public Set<String> getOptions() {
        return options;
    }

    public String getExplanation() {
        return explanation;
    }

    public QuestionTypeEnum getQuestionType() {
        return questionType;
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setOptions(Set<String> options) {
        this.options = options != null ? options : new HashSet<>();
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setQuestionType(QuestionTypeEnum questionType) {
        this.questionType = questionType;
    }

    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes != null ? quizzes : new HashSet<>();
    }

    // equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Question))
            return false;
        Question question = (Question) o;
        return id != null && id.equals(question.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
