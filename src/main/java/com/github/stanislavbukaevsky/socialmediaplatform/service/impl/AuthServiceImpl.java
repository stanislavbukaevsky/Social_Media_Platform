package com.github.stanislavbukaevsky.socialmediaplatform.service.impl;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.JwtRequestDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.JwtResponseDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.RegistrationDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.UserDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Token;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Blocking;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Role;
import com.github.stanislavbukaevsky.socialmediaplatform.exception.UserNameAlreadyExistsException;
import com.github.stanislavbukaevsky.socialmediaplatform.mapper.JwtMapper;
import com.github.stanislavbukaevsky.socialmediaplatform.mapper.UserMapper;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.TokenRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.UserRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.security.UserSecurity;
import com.github.stanislavbukaevsky.socialmediaplatform.security.UserSecurityService;
import com.github.stanislavbukaevsky.socialmediaplatform.service.AuthService;
import com.github.stanislavbukaevsky.socialmediaplatform.service.TokenService;
import com.github.stanislavbukaevsky.socialmediaplatform.utils.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.time.LocalDateTime;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.ExceptionTextMessageConstant.*;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для регистрации и аутентификации пользователей на платформе.
 * Реализует интерфейс {@link AuthService}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserSecurityService userSecurityService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtMapper jwtMapper;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;

    /**
     * Реализация метода регистрации новых пользователей на платформе
     *
     * @param registrationDto класс-DTO для регистрации пользователя на платформе
     * @return Возвращает DTO с информацией о зарегистрированном пользователе
     */
    @Override
    public UserDto userRegistration(RegistrationDto registrationDto) {
        Boolean checkUser = userRepository.existsUserByUsernameAndEmail(registrationDto.getUsername(), registrationDto.getEmail());
        if (checkUser) {
            log.debug(REGISTRATION_MESSAGE_LOGGER_SERVICE_2, registrationDto.getUsername(), registrationDto.getEmail());
            throw new UserNameAlreadyExistsException(REGISTRATION_MESSAGE_EXCEPTION_SERVICE
                    + registrationDto.getUsername()
                    + REGISTRATION_MESSAGE_EXCEPTION_SERVICE_2
                    + registrationDto.getEmail()
                    + REGISTRATION_MESSAGE_EXCEPTION_SERVICE_3);
        } else {
            User user = new User();
            LocalDateTime createdAt = LocalDateTime.now();
            user.setFirstName(registrationDto.getFirstName());
            user.setLastName(registrationDto.getLastName());
            user.setUsername(registrationDto.getUsername());
            user.setEmail(registrationDto.getEmail());
            user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            user.setCreatedAt(createdAt);
            user.setUpdatedAt(createdAt);
            user.setRole(Role.USER);
            user.setBlocking(Blocking.NOT_BAN);
            User result = userRepository.save(user);
            log.info(REGISTRATION_MESSAGE_LOGGER_SERVICE, result);
            return userMapper.toUserDto(result);
        }
    }

    /**
     * Реализация метода аутентификации пользователей на платформе
     *
     * @param jwtRequestDto класс-DTO для аутентификации пользователя на платформе
     * @return Возвращает DTO с информацией об аутентифицированном пользователе на платформе
     * @throws AuthException исключение аутентификации, в случае неправильно введенных личных данных пользователя
     */
    @Override
    public JwtResponseDto userAuthentication(@NonNull JwtRequestDto jwtRequestDto) throws AuthException {
        UserSecurity userSecurityCheck = (UserSecurity) userSecurityService.loadUserByUsername(jwtRequestDto.getUsername());
        if (passwordEncoder.matches(jwtRequestDto.getPassword(), userSecurityCheck.getPassword())) {
            final String username = userSecurityCheck.getUsername();
            log.info(AUTHENTICATION_MESSAGE_LOGGER_SERVICE, jwtRequestDto.getUsername());
            return getGeneratingJwtResponseDto(userSecurityCheck, username);
        } else {
            log.error(AUTHENTICATION_MESSAGE_ERROR_LOGGER_SERVICE);
            throw new AuthException(AUTHENTICATION_MESSAGE_EXCEPTION_SERVICE);
        }
    }

    /**
     * Реализация метода для выдачи нового access-токена для зарегистрированного пользователя на платформе
     *
     * @param refreshToken refresh-токен, выданный пользователю приложением
     * @return Возвращает ответ с личной информацией о пользователе с новым, сгенерированным access-токеном
     * @throws AuthException исключение аутентификации, в случае неправильно введенного refresh-токена
     */
    @Override
    public JwtResponseDto getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String saveRefreshToken = tokenService.findRefreshTokenByUsername(username);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                UserSecurity userSecurityCheck = (UserSecurity) userSecurityService.loadUserByUsername(username);
                final Long id = userSecurityCheck.getUser().getId();
                final String email = userSecurityCheck.getUser().getEmail();
                final String accessToken = jwtProvider.generateAccessToken(userSecurityCheck);
                log.info(GET_ACCESS_TOKEN_MESSAGE_LOGGER_SERVICE, refreshToken);
                return jwtMapper.toJwtDto(id, username, email, accessToken, null);
            }
        }
        log.error(GET_ACCESS_AND_REFRESH_TOKEN_MESSAGE_ERROR_LOGGER_SERVICE, refreshToken);
        throw new AuthException(GET_ACCESS_AND_REFRESH_TOKEN_MESSAGE_EXCEPTION_SERVICE);
    }

    /**
     * Реализация метода для выдачи нового refresh-токена для зарегистрированного пользователя на платформе
     *
     * @param refreshToken refresh-токен, выданный пользователю приложением
     * @return Возвращает ответ с личной информацией о пользователе с новым, сгенерированным refresh-токеном
     * @throws AuthException исключение аутентификации, в случае неправильно введенного refresh-токена
     */
    @Override
    public JwtResponseDto getRefreshToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String saveRefreshToken = tokenService.findRefreshTokenByUsername(username);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                UserSecurity userSecurityCheck = (UserSecurity) userSecurityService.loadUserByUsername(username);
                log.info(GET_REFRESH_TOKEN_MESSAGE_LOGGER_SERVICE, refreshToken);
                return getGeneratingJwtResponseDto(userSecurityCheck, username);
            }
        }
        log.error(GET_ACCESS_AND_REFRESH_TOKEN_MESSAGE_ERROR_LOGGER_SERVICE, refreshToken);
        throw new AuthException(GET_ACCESS_AND_REFRESH_TOKEN_MESSAGE_EXCEPTION_SERVICE);
    }

    /**
     * Приватный метод для генерации ответа с личной информацией о пользователе
     *
     * @param userSecurityCheck объект, для построения объекта аутентификации
     * @param username          имя пользователя (логин)
     * @return Возвращает сгенерированный ответ с личной информацией о пользователе через DTO-класс
     */
    private JwtResponseDto getGeneratingJwtResponseDto(UserSecurity userSecurityCheck, String username) {
        final Long id = userSecurityCheck.getUser().getId();
        final String email = userSecurityCheck.getUser().getEmail();
        final String accessToken = jwtProvider.generateAccessToken(userSecurityCheck);
        final String refreshToken = jwtProvider.generateRefreshToken(userSecurityCheck);
        final LocalDateTime dateTime = LocalDateTime.now();
        Token token = tokenRepository.findTokenByUserId(id).orElseGet(() ->
                tokenService.addToken(refreshToken, userSecurityCheck.getUser(), new Token()));
        if (token.getUser().getId().equals(id)) {
            token.setRefreshToken(refreshToken);
            token.setDateTime(dateTime);
            tokenRepository.save(token);
        }
        log.info(GET_GENERATING_JWT_RESPONSE_DTO_MESSAGE_LOGGER_SERVICE, username);
        return jwtMapper.toJwtDto(id, username, email, accessToken, refreshToken);
    }
}
