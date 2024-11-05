package com.alexcasey.quizzly.response;

public record JwtResponse(
        Long id,
        String username,
        String token) {
}