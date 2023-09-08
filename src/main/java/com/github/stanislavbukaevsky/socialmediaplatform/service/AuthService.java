package com.github.stanislavbukaevsky.socialmediaplatform.service;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.JwtRequestDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.JwtResponseDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.RegistrationDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.UserDto;

import javax.security.auth.message.AuthException;

/**
 * Сервис-интерфейс для регистрации и аутентификации пользователей на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface AuthService {
    /**
     * Сигнатура метода регистрации новых пользователей на платформе
     *
     * @param registrationDto класс-DTO для регистрации пользователя на платформе
     * @return Возвращает DTO с информацией о зарегистрированном пользователе
     */
    UserDto userRegistration(RegistrationDto registrationDto);

    /**
     * Сигнатура метода аутентификации пользователей на платформе
     *
     * @param jwtRequestDto класс-DTO для аутентификации пользователя на платформе
     * @return Возвращает DTO с информацией об аутентифицированном пользователе на платформе
     * @throws AuthException исключение аутентификации, в случае неправильно введенных личных данных пользователя
     */
    JwtResponseDto userAuthentication(JwtRequestDto jwtRequestDto) throws AuthException;

    /**
     * Сигнатура метода для выдачи нового access-токена для зарегистрированного пользователя на платформе
     *
     * @param refreshToken refresh-токен, выданный пользователю приложением
     * @return Возвращает ответ с личной информацией о пользователе с новым, сгенерированным access-токеном
     * @throws AuthException исключение аутентификации, в случае неправильно введенного refresh-токена
     */
    JwtResponseDto getAccessToken(String refreshToken) throws AuthException;

    /**
     * Сигнатура метода для выдачи нового refresh-токена для зарегистрированного пользователя на платформе
     *
     * @param refreshToken refresh-токен, выданный пользователю приложением
     * @return Возвращает ответ с личной информацией о пользователе с новым, сгенерированным refresh-токеном
     * @throws AuthException исключение аутентификации, в случае неправильно введенного refresh-токена
     */
    JwtResponseDto getRefreshToken(String refreshToken) throws AuthException;
}
