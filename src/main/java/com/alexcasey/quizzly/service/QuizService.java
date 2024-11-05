package com.alexcasey.quizzly.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexcasey.quizzly.dto.QuestionDto;
import com.alexcasey.quizzly.dto.QuizDto;
import com.alexcasey.quizzly.exception.QuestionNotFoundException;
import com.alexcasey.quizzly.exception.QuizNotFoundException;
import com.alexcasey.quizzly.exception.UserNotFoundException;
import com.alexcasey.quizzly.mapper.QuestionMapper;
import com.alexcasey.quizzly.mapper.QuizMapper;
import com.alexcasey.quizzly.model.Question;
import com.alexcasey.quizzly.model.Quiz;
import com.alexcasey.quizzly.model.User;
import com.alexcasey.quizzly.repository.QuestionRepository;
import com.alexcasey.quizzly.repository.QuizRepository;
import com.alexcasey.quizzly.repository.UserRepository;
import com.alexcasey.quizzly.utils.Utils;

@Service
public class QuizService {

    private final QuizMapper quizMapper;
    private final QuestionMapper questionMapper;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final Utils utils;

    public QuizService(QuizMapper quizMapper, QuestionMapper questionMapper, QuizRepository quizRepository,
            QuestionRepository questionRepository, UserRepository userRepository, Utils utils) {
        this.quizMapper = quizMapper;
        this.questionMapper = questionMapper;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.utils = utils;
    }

    public QuizDto createQuiz(QuizDto quizDto) {
        User user = utils.getCurrentUser();
        Quiz quiz = quizMapper.toEntity(quizDto);
        quiz.setUser(user); // Set the User entity in the Quiz
        Quiz savedQuiz = quizRepository.save(quiz);
        return quizMapper.toDto(savedQuiz);
    }

    @Transactional
    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public List<QuizDto> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream().map(quizMapper::toDto).collect(Collectors.toList());
    }

    public QuizDto getQuizById(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException("Quiz with id [" + id + "] not found"));
        return quizMapper.toDto(quiz);
    }

    public List<QuizDto> getQuizzesByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id [" + userId + "] not found"));
        List<Quiz> quizzes = quizRepository.findByUser(user);
        return quizzes.stream().map(quizMapper::toDto).collect(Collectors.toList());
    }

    public List<QuizDto> getQuizzesByLoggedInUser() {
        User user = utils.getCurrentUser();
        return getQuizzesByUserId(user.getId());
    }

    @Transactional
    public QuestionDto addQuestionToQuiz(Long quizId, Long questionId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException("Quiz with id [" + quizId + "] not found"));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question with id [" + questionId + "] not found"));
        quiz.getQuestions().add(question);
        question.setQuizzes(new HashSet<>(Arrays.asList(quiz)));
        Question savedQuestion = questionRepository.save(question);
        quizRepository.save(quiz);
        return questionMapper.toDto(savedQuestion);
    }
}
