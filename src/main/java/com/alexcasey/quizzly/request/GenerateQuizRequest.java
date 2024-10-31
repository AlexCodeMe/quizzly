package com.alexcasey.quizzly.request;

import lombok.Data;

import com.alexcasey.quizzly.enums.QuestionTypeEnum;

@Data
public class GenerateQuizRequest {
    private String name;
    private String description;
    private String topic;
    private QuestionTypeEnum questionType;
    private int numberOfQuestions;
}
