package com.alexcasey.quizzly.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.alexcasey.quizzly.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private Long id;
    private String username;
    private Set<RoleEnum> roles;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
