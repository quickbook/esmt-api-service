package com.esmt.response.dto;

public record LoginResponse(
    UserResponse user, 
    String accessToken, 
    String refreshToken, 
    long expiresIn
) {
}