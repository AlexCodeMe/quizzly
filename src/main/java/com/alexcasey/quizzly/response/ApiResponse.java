package com.alexcasey.quizzly.response;

public record ApiResponse(
        String message,
        Object data) {
}
