package com.alexcasey.quizzly.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record QuizDto(
        Long id,
        String name,
        String description,
        String topic,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long userId,
        Set<Long> questionIds,
        Set<Long> quizResultIds) {
}
