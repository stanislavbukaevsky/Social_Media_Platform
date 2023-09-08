package com.github.stanislavbukaevsky.socialmediaplatform.service;

import com.github.stanislavbukaevsky.socialmediaplatform.entity.Token;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;

/**
 * Сервис-интерфейс для всех refresh-токенов, выданных на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface TokenService {
    /**
     * Сигнатура метода для добавления нового refresh-токена в базу данных
     *
     * @param refreshToken refresh-токен
     * @param user         сущность пользователя
     * @param token        сущность токена
     * @return Возвращает добавленный refresh-токен
     */
    Token addToken(String refreshToken, User user, Token token);

    /**
     * Сигнатура метода для поиска refresh-токена по имени пользователя
     *
     * @param username имя пользователя (логин)
     * @return Возвращает найденный refresh-токен
     */
    String findRefreshTokenByUsername(String username);
}
