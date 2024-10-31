package com.alexcasey.quizzly.dto;

import java.util.HashSet;
import java.util.Set;

import com.alexcasey.quizzly.enums.QuestionTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private String question;
    private String answer;
    @Builder.Default
    private Set<String> options = new HashSet<>();
    private String explanation;
    private QuestionTypeEnum questionType;

    @Builder.Default
    private Set<Long> quizIds = new HashSet<>();
}
