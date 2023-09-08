package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс-DTO для получения информации о пользователе и получении jwt-токенов
 */
@Data
@AllArgsConstructor
public class JwtResponseDto {
    @Schema(description = "Идентификатор пользователя")
    private Long id;
    @Schema(description = "Имя пользователя")
    private String username;
    @Schema(description = "Электронная почта пользователя")
    private String email;
    @Schema(description = "Access-токен пользователя")
    private String accessToken;
    @Schema(description = "Refresh-токен пользователя")
    private String refreshToken;
}
