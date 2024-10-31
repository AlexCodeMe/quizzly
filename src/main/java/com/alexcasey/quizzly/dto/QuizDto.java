package com.alexcasey.quizzly.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private Long id;
    private String name;
    private String description;
    private String topic;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long userId;

    @Builder.Default
    private Set<Long> questionIds = new HashSet<>();

    @Builder.Default
    private Set<Long> quizResultIds = new HashSet<>();

}
