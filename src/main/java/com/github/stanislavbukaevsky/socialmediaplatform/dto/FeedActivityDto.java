package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-DTO для получения постов друзей пользователей
 */
@Data
public class FeedActivityDto {
    private Long id;
    private String title;
    private String text;
    private String username;
    private LocalDateTime dateTime;
}
