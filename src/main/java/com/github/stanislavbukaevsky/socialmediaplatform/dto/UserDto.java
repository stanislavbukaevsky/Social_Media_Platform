package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import com.github.stanislavbukaevsky.socialmediaplatform.enums.Blocking;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Класс-DTO для получения данных о зарегистрированном пользователе
 */
@Data
public class UserDto {
    @Schema(description = "Уникальный идентификатор пользователя")
    private Long id;
    @NotEmpty(message = "Поле имени не должно быть пустым!")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов!")
    @Schema(description = "Имя пользователя")
    private String firstName;
    @NotEmpty(message = "Поле фамилии не должно быть пустым!")
    @Size(min = 2, max = 50, message = "Фамилия должна содержать от 2 до 50 символов!")
    @Schema(description = "Фамилия пользователя")
    private String lastName;
    @NotEmpty(message = "Поле логина не должно быть пустым!")
    @Size(min = 5, max = 15, message = "Имя пользователя должно содержать от 5 до 15 символов!")
    @Schema(description = "Имя пользователя при регистрации и авторизации")
    private String username;
    @NotEmpty(message = "Поле электронной почты не должно быть пустым!")
    @Email(message = "Вы ввели не валидную электронную почту!")
    @Schema(description = "Электронная почта пользователя")
    private String email;
    @NotEmpty(message = "Поле пароля не должно быть пустым!")
    @Schema(description = "Пароль пользователя")
    private String password;
    @Schema(description = "Дата регистрации пользователя пользователя")
    private LocalDateTime createdAt;
    @Schema(description = "Дата изменения профиля пользователя")
    private LocalDateTime updatedAt;
    @Schema(description = "Роль пользователя")
    private Role role;
    @Schema(description = "Состояние блокировки пользователя")
    private Blocking blocking;
}
