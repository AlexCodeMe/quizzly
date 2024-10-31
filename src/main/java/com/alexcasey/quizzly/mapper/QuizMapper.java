package com.alexcasey.quizzly.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.alexcasey.quizzly.dto.QuizDto;
import com.alexcasey.quizzly.exception.UserNotFoundException;
import com.alexcasey.quizzly.model.Question;
import com.alexcasey.quizzly.model.Quiz;
import com.alexcasey.quizzly.model.QuizResult;
import com.alexcasey.quizzly.model.User;
import com.alexcasey.quizzly.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuizMapper {

    private final UserRepository userRepository;

    public QuizDto toDto(Quiz quiz) {
        return QuizDto.builder()
                .id(quiz.getId())
                .name(quiz.getName())
                .description(quiz.getDescription())
                .topic(quiz.getTopic())
                .createdAt(quiz.getCreatedAt())
                .updatedAt(quiz.getUpdatedAt())
                .userId(quiz.getUser() != null ? quiz.getUser().getId() : null)
                .questionIds(quiz.getQuestions() != null
                        ? quiz.getQuestions().stream().map(Question::getId)
                                .collect(Collectors.toSet())
                        : Set.of())
                .quizResultIds(quiz.getQuizResults() != null
                        ? quiz.getQuizResults().stream().map(QuizResult::getId)
                                .collect(Collectors.toSet())
                        : Set.of())
                .build();
    }

    public Quiz toEntity(QuizDto quizDto) {
        Quiz quiz = new Quiz();
        quiz.setId(quizDto.getId());
        quiz.setName(quizDto.getName());
        quiz.setDescription(quizDto.getDescription());
        quiz.setTopic(quizDto.getTopic());
        quiz.setCreatedAt(quizDto.getCreatedAt());
        quiz.setUpdatedAt(quizDto.getUpdatedAt());

        if (quizDto.getUserId() != null) {
            User user = userRepository.findById(quizDto.getUserId())
                    .orElseThrow(() -> new UserNotFoundException(
                            "User with id [" + quizDto.getUserId() + "] not found"));
            quiz.setUser(user);
        }

        Set<Question> questions = quizDto.getQuestionIds() != null
                ? quizDto.getQuestionIds().stream().map(id -> {
                    Question question = new Question();
                    question.setId(id);
                    return question;
                }).collect(Collectors.toSet())
                : new HashSet<>();
        quiz.setQuestions(questions);

        Set<QuizResult> quizResults = quizDto.getQuizResultIds() != null
                ? quizDto.getQuizResultIds().stream().map(id -> {
                    QuizResult quizResult = new QuizResult();
                    quizResult.setId(id);
                    return quizResult;
                }).collect(Collectors.toSet())
                : new HashSet<>();
        quiz.setQuizResults(quizResults);

        return quiz;
    }
}