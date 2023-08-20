package com.github.stanislavbukaevsky.socialmediaplatform.service;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.UserDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import org.springframework.web.server.ResponseStatusException;

/**
 * Сервис-интерфейс для всех пользователей зарегистрированных на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface UserService {
    /**
     * Сигнатура метода для поиска пользователей по его логину
     *
     * @param username логин пользователя
     * @return Возвращает найденного пользователя или выбрасывает исключение {@link ResponseStatusException}, если такого не существует
     */
    User findUserByUsername(String username);

    /**
     * Сигнатура метода для просмотра информации об авторизированном пользователе на платформе
     *
     * @param username логин пользователя
     * @return Возвращает DTO найденного по логину пользователя
     */
    UserDto getUserInfo(String username);
}
