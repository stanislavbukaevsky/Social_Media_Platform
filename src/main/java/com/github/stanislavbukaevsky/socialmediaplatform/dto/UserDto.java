package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import com.github.stanislavbukaevsky.socialmediaplatform.enums.Role;
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
    private Long id;
    @NotEmpty(message = "Поле имени не должно быть пустым!")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов!")
    private String firstName;
    @NotEmpty(message = "Поле фамилии не должно быть пустым!")
    @Size(min = 2, max = 50, message = "Фамилия должна содержать от 2 до 50 символов!")
    private String lastName;
    @NotEmpty(message = "Поле логина не должно быть пустым!")
    @Size(min = 5, max = 15, message = "Имя пользователя должно содержать от 5 до 15 символов!")
    private String username;
    @NotEmpty(message = "Поле электронной почты не должно быть пустым!")
    @Email(message = "Вы ввели не валидную электронную почту!")
    private String email;
    @NotEmpty(message = "Поле пароля не должно быть пустым!")
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Role role;
}
