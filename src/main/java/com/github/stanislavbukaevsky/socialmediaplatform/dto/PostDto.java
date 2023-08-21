package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Класс-DTO для подробной информации о посте на платформе
 */
@Data
public class PostDto {
    @Schema(description = "Уникальный идентификатор поста")
    private Long id;
    @Schema(description = "Ссылка на изображение")
    private String image;
    @NotEmpty(message = "Поле с заголовком не должно быть пустым!")
    @Size(min = 1, max = 20, message = "Заголовок должен содержать от 1 до 20 символов!")
    @Schema(description = "Заголовок поста")
    private String title;
    @NotEmpty(message = "Поле с текстом поста не должно быть пустым!")
    @Size(min = 1, max = 2000, message = "Текст поста должен содержать от 1 до 2000 символов!")
    @Schema(description = "Текст поста")
    private String text;
    @Schema(description = "Идентификатор пользователя, опубликовавшего пост")
    private Long author;
    @Schema(description = "Дата опубликования поста")
    private LocalDateTime dateTime;

}
