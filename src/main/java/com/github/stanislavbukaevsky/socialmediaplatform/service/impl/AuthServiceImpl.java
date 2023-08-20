package com.github.stanislavbukaevsky.socialmediaplatform.service.impl;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.JwtRequestDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.JwtResponseDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.RegistrationDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.UserDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Role;
import com.github.stanislavbukaevsky.socialmediaplatform.exception.UserNameAlreadyExistsException;
import com.github.stanislavbukaevsky.socialmediaplatform.mapper.JwtMapper;
import com.github.stanislavbukaevsky.socialmediaplatform.mapper.UserMapper;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.UserRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.security.UserSecurity;
import com.github.stanislavbukaevsky.socialmediaplatform.security.UserSecurityService;
import com.github.stanislavbukaevsky.socialmediaplatform.service.AuthService;
import com.github.stanislavbukaevsky.socialmediaplatform.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.ExceptionTextMessageConstant.REGISTRATION_MESSAGE_EXCEPTION_SERVICE;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.ExceptionTextMessageConstant.REGISTRATION_MESSAGE_EXCEPTION_SERVICE_2;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для регистрации и авторизации пользователей на платформе.
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
    private final JwtTokenUtils jwtTokenUtils;
    private final JwtMapper jwtMapper;

    /**
     * Этот метод реализует регистрацию новых пользователей на платформе
     *
     * @param registrationDto класс-DTO для регистрации пользователя на платформе
     * @return Возвращает DTO с информацией о зарегистрированном пользователе
     */
    @Override
    public UserDto registration(RegistrationDto registrationDto) {
        Boolean checkUser = userRepository.existsUserByUsernameAndEmail(registrationDto.getUsername(), registrationDto.getEmail());
        if (checkUser) {
            log.info(REGISTRATION_MESSAGE_LOGGER_SERVICE_2, registrationDto.getUsername(), registrationDto.getEmail());
            throw new UserNameAlreadyExistsException(REGISTRATION_MESSAGE_EXCEPTION_SERVICE + registrationDto.getUsername() + " " + registrationDto.getEmail() + REGISTRATION_MESSAGE_EXCEPTION_SERVICE_2);
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
            User result = userRepository.save(user);
            log.info(REGISTRATION_MESSAGE_LOGGER_SERVICE, result);
            return userMapper.toUserDto(result);
        }
    }

    /**
     * Этот метод реализует авторизацию пользователей на платформе
     *
     * @param jwtRequestDto класс-DTO для авторизации пользователя на платформе
     * @return Возвращает DTO с информацией о зарегистрированном пользователе
     */
    @Override
    public JwtResponseDto authorization(JwtRequestDto jwtRequestDto) {
        UserSecurity userSecurity = (UserSecurity) userSecurityService.loadUserByUsername(jwtRequestDto.getUsername());
        Long id = userSecurity.getUser().getId();
        String username = userSecurity.getUsername();
        String email = userSecurity.getUser().getEmail();
        String token = jwtTokenUtils.generateToken(userSecurity);
        log.info(AUTHORIZATION_MESSAGE_LOGGER_SERVICE, username);
        return jwtMapper.toJwtDto(id, username, email, token);
    }
}
