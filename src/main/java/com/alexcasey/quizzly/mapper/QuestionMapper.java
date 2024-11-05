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

@Component
public class QuestionMapper {

    private final QuizRepository quizRepository;

    public QuestionMapper(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public QuestionDto toDto(Question question) {
        Set<Long> quizIds = question.getQuizzes() != null
                ? question.getQuizzes().stream()
                        .map(Quiz::getId)
                        .collect(Collectors.toSet())
                : new HashSet<>();

        return new QuestionDto(
                question.getId(),
                question.getQuestion(),
                question.getAnswer(),
                question.getOptions(),
                question.getExplanation(),
                question.getQuestionType(),
                quizIds);
    }

    public Question toEntity(QuestionDto questionDto) {
        Question question = new Question();
        question.setId(questionDto.id());
        question.setQuestion(questionDto.question());
        question.setAnswer(questionDto.answer());
        question.setOptions(questionDto.options());
        question.setExplanation(questionDto.explanation());
        question.setQuestionType(questionDto.questionType());

        Set<Quiz> quizzes = questionDto.quizIds() != null
                ? questionDto.quizIds().stream().map(id -> quizRepository.findById(id)
                        .orElseThrow(() -> new QuizNotFoundException(
                                "Quiz with id [" + id + "] not found")))
                        .collect(Collectors.toSet())
                : new HashSet<>();
        question.setQuizzes(quizzes);

        return question;
    }
}