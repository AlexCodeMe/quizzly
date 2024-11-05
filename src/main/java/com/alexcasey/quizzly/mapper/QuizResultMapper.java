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

@Component
public class QuizResultMapper {

    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    public QuizResultMapper(UserRepository userRepository, QuizRepository quizRepository) {
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
    }

    public QuizResultDto toDto(QuizResult quizResult) {
        return new QuizResultDto(
                quizResult.getId(),
                quizResult.getScore(),
                quizResult.getCompletedAt(),
                quizResult.getUser() != null ? quizResult.getUser().getId() : null,
                quizResult.getQuiz() != null ? quizResult.getQuiz().getId() : null);
    }

    public QuizResult toEntity(QuizResultDto quizResultDto) {
        QuizResult quizResult = new QuizResult();
        quizResult.setId(quizResultDto.id());
        quizResult.setScore(quizResultDto.score());
        quizResult.setCompletedAt(quizResultDto.completedAt());

        if (quizResultDto.userId() != null) {
            User user = userRepository.findById(quizResultDto.userId())
                    .orElseThrow(() -> new UserNotFoundException(
                            "User with id [" + quizResultDto.userId() + "] not found"));
            quizResult.setUser(user);
        }

        if (quizResultDto.quizId() != null) {
            Quiz quiz = quizRepository.findById(quizResultDto.quizId())
                    .orElseThrow(() -> new QuizNotFoundException(
                            "Quiz with id [" + quizResultDto.quizId() + "] not found"));
            quizResult.setQuiz(quiz);
        }

        return quizResult;
    }
}
