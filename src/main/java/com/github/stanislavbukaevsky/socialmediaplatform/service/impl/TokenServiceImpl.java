package com.github.stanislavbukaevsky.socialmediaplatform.service.impl;

import com.github.stanislavbukaevsky.socialmediaplatform.entity.Token;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.TokenRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.service.TokenService;
import com.github.stanislavbukaevsky.socialmediaplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.ADD_TOKEN_MESSAGE_LOGGER_SERVICE;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.FIND_REFRESH_TOKEN_BY_USERNAME_MESSAGE_LOGGER_SERVICE;

/**
 * Сервис-класс с бизнес-логикой всех refresh-токенов, выданных на платформе.
 * Реализует интерфейс {@link TokenService}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final UserService userService;

    /**
     * Реализация метода для добавления нового refresh-токена в базу данных
     *
     * @param refreshToken refresh-токен
     * @param user         сущность пользователя
     * @param token        сущность токена
     * @return Возвращает добавленный refresh-токен
     */
    @Override
    public Token addToken(String refreshToken, User user, Token token) {
        LocalDateTime dateTime = LocalDateTime.now();
        token.setRefreshToken(refreshToken);
        token.setDateTime(dateTime);
        token.setUser(user);
        log.info(ADD_TOKEN_MESSAGE_LOGGER_SERVICE);
        return tokenRepository.save(token);
    }

    /**
     * Реализация метода для поиска refresh-токена по имени пользователя
     *
     * @param username имя пользователя (логин)
     * @return Возвращает найденный refresh-токен
     */
    @Override
    public String findRefreshTokenByUsername(String username) {
        User user = userService.findUserByUsername(username);
        log.info(FIND_REFRESH_TOKEN_BY_USERNAME_MESSAGE_LOGGER_SERVICE, username);
        return user.getToken().getRefreshToken();
    }
}
