package com.alexcasey.quizzly.dto;

import java.time.LocalDateTime;

public record QuizResultDto(
        Long id,
        double score,
        LocalDateTime completedAt,
        Long userId,
        Long quizId) {
}
