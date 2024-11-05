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

@Component
public class QuizMapper {

    private final UserRepository userRepository;

    public QuizMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public QuizDto toDto(Quiz quiz) {
        return new QuizDto(
                quiz.getId(),
                quiz.getName(),
                quiz.getDescription(),
                quiz.getTopic(),
                quiz.getCreatedAt(),
                quiz.getUpdatedAt(),
                quiz.getUser().getId(),
                quiz.getQuestions() != null
                        ? quiz.getQuestions().stream()
                                .map(Question::getId)
                                .collect(Collectors.toSet())
                        : new HashSet<>(),
                quiz.getQuizResults() != null
                        ? quiz.getQuizResults().stream()
                                .map(QuizResult::getId)
                                .collect(Collectors.toSet())
                        : new HashSet<>());
    }

    public Quiz toEntity(QuizDto quizDto) {
        Quiz quiz = new Quiz();
        quiz.setId(quizDto.id());
        quiz.setName(quizDto.name());
        quiz.setDescription(quizDto.description());
        quiz.setTopic(quizDto.topic());
        quiz.setCreatedAt(quizDto.createdAt());
        quiz.setUpdatedAt(quizDto.updatedAt());

        if (quizDto.userId() != null) {
            User user = userRepository.findById(quizDto.userId())
                    .orElseThrow(() -> new UserNotFoundException(
                            "User with id [" + quizDto.userId() + "] not found"));
            quiz.setUser(user);
        }

        Set<Question> questions = quizDto.questionIds() != null
                ? quizDto.questionIds().stream().map(id -> {
                    Question question = new Question();
                    question.setId(id);
                    return question;
                }).collect(Collectors.toSet())
                : new HashSet<>();
        quiz.setQuestions(questions);

        Set<QuizResult> quizResults = quizDto.quizResultIds() != null
                ? quizDto.quizResultIds().stream().map(id -> {
                    QuizResult quizResult = new QuizResult();
                    quizResult.setId(id);
                    return quizResult;
                }).collect(Collectors.toSet())
                : new HashSet<>();
        quiz.setQuizResults(quizResults);

        return quiz;
    }
}