package com.alexcasey.quizzly.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.StructuredOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.alexcasey.quizzly.dto.AiQuestionDto;
import com.alexcasey.quizzly.enums.QuestionTypeEnum;

@Service
public class AiServiceV2 {

    private final ChatModel chatModel;

    public AiServiceV2(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public List<AiQuestionDto> generateQuestions(
            String name,
            String description,
            String topic,
            QuestionTypeEnum questionType,
            int numberOfQuestions) {

        StructuredOutputConverter<List<AiQuestionDto>> converter = new BeanOutputConverter<>(
                new ParameterizedTypeReference<List<AiQuestionDto>>() {
                });

        String inputTemplate = """
                You are a helpful assistant that generates quiz questions based on the topic {topic}
                and description {description}. The quiz should be composed of {numberOfQuestions}
                questions with the type {questionType} questions.
                Please include the following for each question:
                1. the Question
                2. the Answer
                3. the Explanation
                4. if the question type is MULTIPLE_CHOICE, include the Options.
                   if the question type is FILL_IN_THE_BLANK, include the blank to fill in.
                   For SHORT_ANSWER, provide a generic sample answer.
                   Multiple choice questions should have between 3 to 8 options.
                {format}
                """;

        Prompt aiPrompt = new Prompt(
                new PromptTemplate(inputTemplate,
                        Map.of(
                                "topic", topic,
                                "description", description,
                                "numberOfQuestions", String.valueOf(numberOfQuestions),
                                "questionType", questionType.toString(),
                                "format", converter.getFormat()))
                        .createMessage());

        Generation generation = chatModel.call(aiPrompt).getResult();
        return converter.convert(generation.getOutput().getContent());
    }
}
