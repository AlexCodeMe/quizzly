package com.alexcasey.quizzly.dto;

import java.util.Set;

import com.alexcasey.quizzly.enums.QuestionTypeEnum;

public record QuestionDto(
        Long id,
        String question,
        String answer,
        Set<String> options,
        String explanation,
        QuestionTypeEnum questionType,
        Set<Long> quizIds) {
}
