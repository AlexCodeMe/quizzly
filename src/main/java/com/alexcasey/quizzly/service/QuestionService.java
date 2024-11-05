package com.alexcasey.quizzly.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alexcasey.quizzly.dto.QuestionDto;
import com.alexcasey.quizzly.exception.QuestionNotFoundException;
import com.alexcasey.quizzly.mapper.QuestionMapper;
import com.alexcasey.quizzly.model.Question;
import com.alexcasey.quizzly.repository.QuestionRepository;

import jakarta.transaction.Transactional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public QuestionService(QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    public QuestionDto createQuestion(QuestionDto questionDto) {
        Question question = questionMapper.toEntity(questionDto);
        Question savedQuestion = questionRepository.save(question);
        return questionMapper.toDto(savedQuestion);
    }

    @Transactional
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public QuestionDto getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question " + id + " not found"));
        return questionMapper.toDto(question);
    }

    public List<QuestionDto> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream().map(questionMapper::toDto).collect(Collectors.toList());
    }
}
