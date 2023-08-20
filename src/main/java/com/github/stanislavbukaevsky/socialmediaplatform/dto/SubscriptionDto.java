package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import com.github.stanislavbukaevsky.socialmediaplatform.enums.Status;
import lombok.Data;

/**
 * Класс-DTO для подписок пользователей
 */
@Data
public class SubscriptionDto {
    private Integer senderApp;
    private Integer recipientApp;
    private Status status;
    private String description;
}
