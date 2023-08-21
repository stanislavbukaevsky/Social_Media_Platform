package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-DTO для получения личной переписки пользователей
 */
@Data
public class CorrespondenceDto {
    @Schema(description = "Уникальный идентификатор сообщения")
    private Long id;
    @Schema(description = "Имя пользователя отправившего сообщение")
    private String usernameSender;
    @Schema(description = "Имя пользователя получившего сообщение")
    private String usernameRecipient;
    @Schema(description = "Текстовое сообщение отправленное пользователю")
    private String text;
    @Schema(description = "Подробное описание действия")
    private String description;
    @Schema(description = "Дата отправления сообщения другому пользователю")
    private LocalDateTime dateTime;
}
