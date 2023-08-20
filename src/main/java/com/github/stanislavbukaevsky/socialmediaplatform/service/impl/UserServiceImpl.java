package com.github.stanislavbukaevsky.socialmediaplatform.service.impl;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.UserDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import com.github.stanislavbukaevsky.socialmediaplatform.mapper.UserMapper;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.UserRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.ExceptionTextMessageConstant.FIND_USER_BY_USERNAME_MESSAGE_EXCEPTION_SERVICE;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.FIND_USER_BY_USERNAME_MESSAGE_LOGGER_SERVICE;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.GET_USER_INFO_MESSAGE_LOGGER_SERVICE;

/**
 * Сервис-класс с бизнес-логикой для всех пользователей зарегистрированных на платформе.
 * Реализует интерфейс {@link UserService}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Этот метод реализует поиск пользователей по его логину
     *
     * @param username логин пользователя
     * @return Возвращает найденного пользователя или выбрасывает исключение {@link ResponseStatusException}, если такого не существует
     */
    @Override
    public User findUserByUsername(String username) {
        log.info(FIND_USER_BY_USERNAME_MESSAGE_LOGGER_SERVICE, username);
        return userRepository.findUserByUsername(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, FIND_USER_BY_USERNAME_MESSAGE_EXCEPTION_SERVICE));
    }

    /**
     * Реализация метода для просмотра информации об авторизированном пользователе на платформе
     *
     * @param username логин пользователя
     * @return Возвращает DTO найденного по логину пользователя
     */
    @Override
    public UserDto getUserInfo(String username) {
        User user = findUserByUsername(username);
        log.info(GET_USER_INFO_MESSAGE_LOGGER_SERVICE, username);
        return userMapper.toUserDto(user);
    }

}
