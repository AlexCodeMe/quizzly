package com.alexcasey.quizzly.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.alexcasey.quizzly.dto.QuestionDto;
import com.alexcasey.quizzly.exception.QuizNotFoundException;
import com.alexcasey.quizzly.model.Question;
import com.alexcasey.quizzly.model.Quiz;
import com.alexcasey.quizzly.repository.QuizRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

    private final QuizRepository quizRepository;

    public QuestionDto toDto(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .answer(question.getAnswer())
                .options(question.getOptions())
                .explanation(question.getExplanation())
                .questionType(question.getQuestionType())
                .quizIds(question.getQuizzes() != null
                        ? question.getQuizzes().stream()
                                .map(Quiz::getId)
                                .collect(Collectors.toSet())
                        : new HashSet<>())
                .build();
    }

    public Question toEntity(QuestionDto questionDto) {
        Question question = new Question();
        question.setId(questionDto.getId());
        question.setQuestion(questionDto.getQuestion());
        question.setAnswer(questionDto.getAnswer());
        question.setOptions(questionDto.getOptions());
        question.setExplanation(questionDto.getExplanation());
        question.setQuestionType(questionDto.getQuestionType());

        Set<Quiz> quizzes = questionDto.getQuizIds() != null
                ? questionDto.getQuizIds().stream().map(id -> quizRepository.findById(id)
                        .orElseThrow(() -> new QuizNotFoundException("Quiz with id [" + id + "] not found")))
                        .collect(Collectors.toSet())
                : new HashSet<>();
        question.setQuizzes(quizzes);

        return question;
    }
}