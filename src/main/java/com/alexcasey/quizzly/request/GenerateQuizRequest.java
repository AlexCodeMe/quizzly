package com.alexcasey.quizzly.request;

import com.alexcasey.quizzly.enums.QuestionTypeEnum;

public record GenerateQuizRequest(
    String name,
    String description,
    String topic,
    QuestionTypeEnum questionType,
    int numberOfQuestions
) {}