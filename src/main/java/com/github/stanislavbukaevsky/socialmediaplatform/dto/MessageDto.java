package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Текстовое сообщение отправленное пользователю")
    private String text;
    @Schema(description = "Идентификатор пользователя, который отправляет сообщение другому пользователю")
    private Integer senderMsg;
    @Schema(description = "Идентификатор пользователя, который получает сообщение от другого пользователя")
    private Integer recipientMsg;
    @Schema(description = "Дата отправления сообщения другому пользователю")
    private LocalDateTime dateTime;
    @Schema(description = "Подробное описание действия")
    private String description;
}
