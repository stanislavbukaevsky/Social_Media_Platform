package com.github.stanislavbukaevsky.socialmediaplatform.service;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.JwtRequestDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.JwtResponseDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.RegistrationDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.UserDto;

/**
 * Сервис-интерфейс для регистрации и авторизации пользователей на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface AuthService {
    /**
     * Сигнатура метода регистрации новых пользователей на платформе
     *
     * @param registrationDto класс-DTO для регистрации пользователя на платформе
     * @return Возвращает DTO с информацией о зарегистрированном пользователе
     */
    UserDto registration(RegistrationDto registrationDto);

    /**
     * Сигнатура метода авторизации пользователей на платформе
     *
     * @param jwtRequestDto класс-DTO для авторизации пользователя на платформе
     * @return Возвращает DTO с информацией о зарегистрированном пользователе
     */
    JwtResponseDto authorization(JwtRequestDto jwtRequestDto);
}
