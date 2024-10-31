package com.alexcasey.quizzly.mapper;

import org.springframework.stereotype.Component;

import com.alexcasey.quizzly.dto.QuizResultDto;
import com.alexcasey.quizzly.exception.QuizNotFoundException;
import com.alexcasey.quizzly.exception.UserNotFoundException;
import com.alexcasey.quizzly.model.Quiz;
import com.alexcasey.quizzly.model.QuizResult;
import com.alexcasey.quizzly.model.User;
import com.alexcasey.quizzly.repository.QuizRepository;
import com.alexcasey.quizzly.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class QuizResultMapper {

    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    public QuizResultDto toDto(QuizResult quizResult) {
        return QuizResultDto.builder()
                .id(quizResult.getId())
                .score(quizResult.getScore())
                .completedAt(quizResult.getCompletedAt())
                .userId(quizResult.getUser() != null ? quizResult.getUser().getId() : null)
                .quizId(quizResult.getQuiz() != null ? quizResult.getQuiz().getId() : null)
                .build();
    }

    public QuizResult toEntity(QuizResultDto quizResultDto) {
        QuizResult quizResult = new QuizResult();
        quizResult.setId(quizResultDto.getId());
        quizResult.setScore(quizResultDto.getScore());
        quizResult.setCompletedAt(quizResultDto.getCompletedAt());

        if (quizResultDto.getUserId() != null) {
            User user = userRepository.findById(quizResultDto.getUserId())
                    .orElseThrow(() -> new UserNotFoundException(
                            "User with id [" + quizResultDto.getUserId() + "] not found"));
            quizResult.setUser(user);
        }

        if (quizResultDto.getQuizId() != null) {
            Quiz quiz = quizRepository.findById(quizResultDto.getQuizId())
                    .orElseThrow(() -> new QuizNotFoundException(
                            "Quiz with id [" + quizResultDto.getQuizId() + "] not found"));
            quizResult.setQuiz(quiz);
        }

        return quizResult;
    }
}
