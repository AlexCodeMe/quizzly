package com.alexcasey.quizzly.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.alexcasey.quizzly.dto.UserDto;
import com.alexcasey.quizzly.model.Quiz;
import com.alexcasey.quizzly.model.QuizResult;
import com.alexcasey.quizzly.model.User;

@Component
public class UserMapper {

    /**
     * Converts a User entity to a UserDto.
     *
     * @param user the User entity to convert
     * @return the resulting UserDto
     */
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        Long accountId = user.getAccount() != null ? user.getAccount().getId() : null;

        Set<Long> quizIds = user.getQuizzes() != null
                ? user.getQuizzes().stream()
                        .map(Quiz::getId)
                        .collect(Collectors.toSet())
                : new HashSet<>();

        Set<Long> quizResultIds = user.getQuizResults() != null
                ? user.getQuizResults().stream()
                        .map(QuizResult::getId)
                        .collect(Collectors.toSet())
                : new HashSet<>();

        return new UserDto(
                user.getId(),
                accountId,
                user.getAccount() != null ? user.getAccount().getCreatedAt() : null,
                user.getAccount() != null ? user.getAccount().getUpdatedAt() : null,
                quizIds,
                quizResultIds);
    }

    /**
     * Converts a UserDto to a User entity.
     *
     * @param userDto the UserDto to convert
     * @return the resulting User entity
     */
    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setId(userDto.id());

        Set<Quiz> quizzes = userDto.quizIds() != null
                ? userDto.quizIds().stream()
                        .map(id -> {
                            Quiz quiz = new Quiz();
                            quiz.setId(id);
                            return quiz;
                        })
                        .collect(Collectors.toSet())
                : new HashSet<>();
        user.setQuizzes(quizzes);

        Set<QuizResult> quizResults = userDto.quizResultIds() != null
                ? userDto.quizResultIds().stream()
                        .map(id -> {
                            QuizResult quizResult = new QuizResult();
                            quizResult.setId(id);
                            return quizResult;
                        })
                        .collect(Collectors.toSet())
                : new HashSet<>();
        user.setQuizResults(quizResults);

        return user;
    }
}
