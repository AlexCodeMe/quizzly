package com.alexcasey.quizzly.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record UserDto(
        Long id,
        Long accountId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Set<Long> quizIds,
        Set<Long> quizResultIds) {
}
