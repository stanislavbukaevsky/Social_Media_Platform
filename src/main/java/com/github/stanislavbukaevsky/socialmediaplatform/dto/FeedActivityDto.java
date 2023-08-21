package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-DTO для получения постов друзей пользователей
 */
@Data
public class FeedActivityDto {
    @Schema(description = "Уникальный идентификатор поста")
    private Long id;
    @Schema(description = "Заголовок поста")
    private String title;
    @Schema(description = "Текст поста")
    private String text;
    @Schema(description = "Имя пользователя")
    private String username;
    @Schema(description = "Дата опубликования поста")
    private LocalDateTime dateTime;
}
