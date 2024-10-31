package com.alexcasey.quizzly.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultDto {
    private Long id;
    private double score;
    private LocalDateTime completedAt;

    private Long userId;
    private Long quizId;
}
