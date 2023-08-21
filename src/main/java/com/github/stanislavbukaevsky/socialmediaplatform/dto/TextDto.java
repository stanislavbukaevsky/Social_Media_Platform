package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Класс-DTO для отправки личного сообщения пользователю
 */
@Data
public class TextDto {
    @NotEmpty(message = "Поле с текстом сообщения не должно быть пустым!")
    @Size(min = 1, max = 500, message = "Текст сообщения должен содержать от 1 до 500 символов!")
    @Schema(description = "Текстовое сообщение")
    private String text;
}
