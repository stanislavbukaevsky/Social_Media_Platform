package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import com.github.stanislavbukaevsky.socialmediaplatform.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-DTO для получения информации о изменении роли пользователя
 */
@Data
public class ChangeRoleDto {
    @Schema(description = "Имя пользователя, который меняет роль другого пользователя")
    private String senderUsername;
    @Schema(description = "Имя пользователя, у которого будет изменена роль")
    private String recipientUsername;
    @Schema(description = "Дата и время совершенного действия")
    private LocalDateTime dateTime;
    @Schema(description = "Роль пользователя")
    private Role role;
    @Schema(description = "Описание действия")
    private String description;
}
