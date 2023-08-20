package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Класс-DTO для отправки сообщения пользователю
 */
@Data
public class MessageDto {
    @NotEmpty(message = "Поле с текстом сообщения не должно быть пустым!")
    @Size(min = 1, max = 500, message = "Текст сообщения должен содержать от 1 до 500 символов!")
    private String text;
    private Integer senderMsg;
    private Integer recipientMsg;
    private LocalDateTime dateTime;
    private String description;
}
