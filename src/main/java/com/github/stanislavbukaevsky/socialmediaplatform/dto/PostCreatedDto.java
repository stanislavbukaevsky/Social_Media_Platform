package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Класс-DTO для размещения постов на платформу
 */
@Data
public class PostCreatedDto {
    @NotEmpty(message = "Поле с заголовком не должно быть пустым!")
    @Size(min = 1, max = 20, message = "Заголовок должен содержать от 1 до 20 символов!")
    private String title;
    @NotEmpty(message = "Поле с текстом поста не должно быть пустым!")
    @Size(min = 1, max = 2000, message = "Текст поста должен содержать от 1 до 2000 символов!")
    private String text;
    private String image;
}
