package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс-DTO для получения информации о пользователе по jwt-токену
 */
@Data
@AllArgsConstructor
public class JwtResponseDto {
    private Long id;
    private String username;
    private String email;
    private String token;
}
