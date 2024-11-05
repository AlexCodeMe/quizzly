package com.alexcasey.quizzly.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.alexcasey.quizzly.enums.RoleEnum;

public record AccountDto(
        Long id,
        String username,
        Set<RoleEnum> roles,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
