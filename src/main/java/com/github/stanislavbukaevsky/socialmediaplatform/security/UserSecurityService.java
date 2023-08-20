package com.github.stanislavbukaevsky.socialmediaplatform.security;

import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import com.github.stanislavbukaevsky.socialmediaplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.LOAD_USER_BY_USERNAME_MESSAGE_LOGGER_SECURITY;

/**
 * Этот класс используется для поиска пользователей в базе данных, через этот сервис.
 * Реализует интерфейс {@link UserDetailsService}
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {
    private final UserService userService;
    private final UserSecurity userSecurity;

    /**
     * Этот метод ищет пользователя по его уникальному логину
     *
     * @param username логин пользователя
     * @return Возвращает найденного пользователя, через защищенный сервис {@link UserSecurity}
     * @throws UsernameNotFoundException исключение, если пользователя с таким логином не существует
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        userSecurity.setUser(user);
        log.info(LOAD_USER_BY_USERNAME_MESSAGE_LOGGER_SECURITY, userSecurity.getUsername());
        return userSecurity;
    }
}
