package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Класс-DTO для отдачи jwt-токена
 */
@Data
public class JwtRequestDto {
    @NotEmpty(message = "Поле логина не должно быть пустым!")
    @Size(min = 5, max = 15, message = "Имя пользователя должно содержать от 5 до 15 символов!")
    @Schema(description = "Имя пользователя (логин)")
    private String username;
    @NotEmpty(message = "Поле пароля не должно быть пустым!")
    @Schema(description = "Пароль пользователя")
    private String password;
}
