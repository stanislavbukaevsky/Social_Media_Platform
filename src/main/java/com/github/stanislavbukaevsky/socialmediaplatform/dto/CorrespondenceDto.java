package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-DTO для получения личной переписки пользователей
 */
@Data
public class CorrespondenceDto {
    private Long id;
    private String usernameSender;
    private String usernameRecipient;
    private String text;
    private String description;
    private LocalDateTime dateTime;
}
