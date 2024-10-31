package com.alexcasey.quizzly.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private Long id;
    private Long accountId;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @Builder.Default
    Set<Long> quizIds = new HashSet<>();
    @Builder.Default
    Set<Long> quizResultIds = new HashSet<>();
}
