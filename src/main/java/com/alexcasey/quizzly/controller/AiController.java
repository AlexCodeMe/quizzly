package com.alexcasey.quizzly.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// import com.alexcasey.quizzly.service.AiService;
import com.alexcasey.quizzly.service.AiServiceV2;
import com.alexcasey.quizzly.dto.AiQuestionDto;
// import com.alexcasey.quizzly.service.QuestionService;
// import com.alexcasey.quizzly.service.QuizService;
import com.alexcasey.quizzly.request.GenerateQuestionsRequest;
// import com.alexcasey.quizzly.request.GenerateQuizRequest;
// import com.alexcasey.quizzly.dto.AiQuestionDto;
// import com.alexcasey.quizzly.dto.QuestionDto;
// import com.alexcasey.quizzly.dto.QuizDto;
// import com.alexcasey.quizzly.model.Quiz;
// import com.alexcasey.quizzly.model.Question;
// import com.alexcasey.quizzly.mapper.QuestionMapper;
// import com.alexcasey.quizzly.mapper.QuizMapper;

@RestController
@RequestMapping("${api.prefix}/ai")
public class AiController {

    // private final AiService aiService;
    private final AiServiceV2 aiServiceV2;
    // private final QuizService quizService;
    // private final QuestionService questionService;
    // private final QuestionMapper questionMapper;
    // private final QuizMapper quizMapper;

    public AiController(AiServiceV2 aiServiceV2) {
        this.aiServiceV2 = aiServiceV2;
    }

    @PostMapping("/generate-questions-v2")
    public ResponseEntity<List<AiQuestionDto>> generateQuestionsV2(@RequestBody GenerateQuestionsRequest request) {
        return ResponseEntity.ok(aiServiceV2.generateQuestions(request.name(), request.description(),
                request.topic(), request.questionType(), request.numberOfQuestions()));
    }

    // @PostMapping("/ai-response")
    // public ResponseEntity<String> generateAiResponse(@RequestBody GenerateQuestionsRequest request) {
    //     String aiGeneratedContent = aiService.generateQuiz(
    //             request.getName(),
    //             request.getDescription(),
    //             request.getTopic(),
    //             request.getQuestionType(),
    //             request.getNumberOfQuestions());
    //     log.info("AI Generated Content: {}", aiGeneratedContent);
    //     return ResponseEntity.ok(aiGeneratedContent);
    // }

    // @PostMapping("/generate-questions")
    // public ResponseEntity<Set<QuestionDto>> generateQuestions(@RequestBody GenerateQuestionsRequest request) {
    //     String aiGeneratedContent = aiService.generateQuiz(
    //             request.getName(),
    //             request.getDescription(),
    //             request.getTopic(),
    //             request.getQuestionType(),
    //             request.getNumberOfQuestions());
    //     log.info("AI Generated Content: {}", aiGeneratedContent);

    //     QuizDto generatedQuizDto = aiService.parseAiResponse(aiGeneratedContent, request.getName(), request.getTopic(),
    //             request.getDescription(), request.getQuestionType());

    //     Set<Question> questions = new HashSet<>();
    //     for (Long questionId : generatedQuizDto.getQuestionIds()) {
    //         Question question = questionMapper.toEntity(questionService.getQuestionById(questionId));
    //         questions.add(question);
    //     }
    //     return ResponseEntity.ok(questions.stream()
    //             .map(questionMapper::toDto)
    //             .collect(Collectors.toSet()));
    // }

    // @PostMapping("/generate-quiz")
    // public ResponseEntity<QuizDto> generateQuiz(@RequestBody GenerateQuizRequest request) {

    //     // 1. Generate quiz questions
    //     String aiGeneratedContent = aiService.generateQuiz(
    //             request.getName(),
    //             request.getDescription(),
    //             request.getTopic(),
    //             request.getQuestionType(),
    //             request.getNumberOfQuestions());
    //     System.out.println("AI Generated Content: " + aiGeneratedContent);

    //     QuizDto generatedQuizDto = aiService.parseAiResponse(aiGeneratedContent, request.getName(), request.getTopic(),
    //             request.getDescription(), request.getQuestionType());

    //     // 3. Create a new Quiz entity and populate it
    //     Quiz generatedQuiz = quizMapper.toEntity(generatedQuizDto);

    //     Set<Question> questions = new HashSet<>();
    //     for (Long questionId : generatedQuizDto.getQuestionIds()) {
    //         // Find and map each question by ID, associating it with the quiz
    //         Question question = questionMapper.toEntity(questionService.getQuestionById(questionId));
    //         question.getQuizzes().add(generatedQuiz); // Link the question to the quiz
    //         questions.add(question);
    //     }

    //     // Set the questions in the quiz
    //     generatedQuiz.setQuestions(questions);

    //     // Save the quiz
    //     Quiz savedQuiz = quizService.save(generatedQuiz);

    //     // Return the saved quiz as DTO
    //     return ResponseEntity.ok(quizMapper.toDto(savedQuiz));
    // }
}
