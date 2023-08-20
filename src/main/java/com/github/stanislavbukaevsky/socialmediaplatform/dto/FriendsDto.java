package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import com.github.stanislavbukaevsky.socialmediaplatform.enums.Status;
import lombok.Data;

/**
 * Класс-DTO для подачи заявки в друзья
 */
@Data
public class FriendsDto {
    private Integer senderApp;
    private String usernameSender;
    private Integer recipientApp;
    private String usernameRecipient;
    private Status status;
    private String description;
}
