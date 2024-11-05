package com.alexcasey.quizzly.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexcasey.quizzly.dto.QuestionDto;
import com.alexcasey.quizzly.dto.QuizDto;
import com.alexcasey.quizzly.service.QuizService;

@RestController
@RequestMapping("${api.prefix}/quizzes")
public class QuizController {
    
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public QuizDto createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto);
    }

    @GetMapping("/{id}")
    public QuizDto getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("/user/{userId}")
    public List<QuizDto> getQuizzesByUserId(@PathVariable Long userId) {
        return quizService.getQuizzesByUserId(userId);
    }

    @GetMapping
    public List<QuizDto> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @PostMapping("/{quizId}/questions/{questionId}")
    public QuestionDto addQuestionToQuiz(@PathVariable Long quizId, @PathVariable Long questionId) {
        return quizService.addQuestionToQuiz(quizId, questionId);
    }
}
