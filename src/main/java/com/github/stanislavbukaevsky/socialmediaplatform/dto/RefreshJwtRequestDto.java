package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Класс-DTO для отправки информации о refresh-токене
 */
@Data
public class RefreshJwtRequestDto {
    @NotEmpty(message = "Поле refresh-токена не должно быть пустым!")
    @Schema(description = "Refresh-токен пользователя")
    private String refreshToken;
}
