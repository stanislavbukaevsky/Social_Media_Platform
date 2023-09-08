package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import com.github.stanislavbukaevsky.socialmediaplatform.enums.Blocking;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-DTO для получения информации о блокировки пользователей
 */
@Data
public class BlockingDto {
    @Schema(description = "Имя пользователя, который блокирует другого пользователя")
    private String senderUsername;
    @Schema(description = "Имя пользователя, которого хотят забанить")
    private String recipientUsername;
    @Schema(description = "Дата и время совершенного действия")
    private LocalDateTime dateTime;
    @Schema(description = "Статус блокировки")
    private Blocking blocking;
    @Schema(description = "Описание действия")
    private String description;
}
