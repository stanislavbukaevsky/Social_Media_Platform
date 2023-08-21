package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import com.github.stanislavbukaevsky.socialmediaplatform.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс-DTO для подписок пользователей
 */
@Data
public class SubscriptionDto {
    @Schema(description = "Идентификатор пользователя, который хочет подписаться")
    private Integer senderApp;
    @Schema(description = "Идентификатор пользователя, на которого пользователь хочет подписаться")
    private Integer recipientApp;
    @Schema(description = "Статус отправленной заявки")
    private Status status;
    @Schema(description = "Подробное описание действия")
    private String description;
}
