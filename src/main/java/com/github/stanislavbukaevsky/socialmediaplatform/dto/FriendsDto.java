package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import com.github.stanislavbukaevsky.socialmediaplatform.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс-DTO для подачи заявки в друзья
 */
@Data
public class FriendsDto {
    @Schema(description = "Идентификатор пользователя отправляющий заявку в друзья")
    private Integer senderApp;
    @Schema(description = "Имя пользователя отправляющий заявку в друзья")
    private String usernameSender;
    @Schema(description = "Идентификатор пользователя получающий заявку в друзья")
    private Integer recipientApp;
    @Schema(description = "Имя пользователя получившего заявку в друзья")
    private String usernameRecipient;
    @Schema(description = "Статус отправленной заявки в друзья")
    private Status status;
    @Schema(description = "Подробное описание действия")
    private String description;
}
