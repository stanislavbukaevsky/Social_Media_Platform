package com.github.stanislavbukaevsky.socialmediaplatform.controller;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.*;
import com.github.stanislavbukaevsky.socialmediaplatform.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.*;

/**
 * Класс-контроллер для работы с регистрацией и аутентификацией пользователей на платформе
 */
@RestController
@RequestMapping("/inputs")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Работа с регистрацией и аутентификацией", description = "Позволяет управлять методами по работе с регистрацией и аутентификацией пользователей на платформе")
public class AuthController {
    private final AuthService authService;

    /**
     * Этот метод позволяет зарегистрировать нового пользователя на платформе
     *
     * @param registrationDto класс-DTO для регистрации пользователя на платформе
     * @return Возвращает зарегистрированного пользователя и всю информацию о нем
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый пользователь зарегистрирован (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера (Internal Server Error)")
    })
    @Operation(summary = "Метод регистрации пользователей на платформе", description = "Позволяет зарегистрироваться новому пользователю на платформе")
    @PostMapping("/registration")
    public ResponseEntity<UserDto> userRegistration(@RequestBody @Valid RegistrationDto registrationDto) {
        UserDto user = authService.userRegistration(registrationDto);
        log.info(REGISTRATION_MESSAGE_LOGGER_CONTROLLER, registrationDto.getUsername(), registrationDto.getEmail());
        return ResponseEntity.ok(user);
    }

    /**
     * Этот метод позволяет аутентифицироваться (войти в приложение) пользователю на платформе
     *
     * @param jwtRequestDto класс-DTO для аутентификации пользователя на платформе
     * @return Возвращает аутентифицированного пользователя, если такой существует
     * @throws AuthException исключение аутентификации, в случае неправильно введенных личных данных пользователя
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно аутентифицирован (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = JwtResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)")
    })
    @Operation(summary = "Метод аутентификации пользователей на платформе", description = "Позволяет аутентифицироваться зарегистрированному пользователю на платформе")
    @PostMapping("/authentication")
    public ResponseEntity<JwtResponseDto> userAuthentication(@RequestBody @Valid JwtRequestDto jwtRequestDto) throws AuthException {
        JwtResponseDto jwtResponseDto = authService.userAuthentication(jwtRequestDto);
        log.info(AUTHENTICATION_MESSAGE_LOGGER_CONTROLLER, jwtRequestDto.getUsername());
        return ResponseEntity.ok(jwtResponseDto);
    }

    /**
     * Этот метод позволяет получить новый access-токен для зарегистрированного пользователя на платформе
     *
     * @param refreshToken класс-DTO с refresh-токеном, выданный пользователю приложением
     * @return Возвращает ответ с личной информацией о пользователе с новым, сгенерированным access-токеном
     * @throws AuthException исключение аутентификации, в случае неправильно введенного refresh-токена
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый access-токен успешно выдан (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = JwtResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)")
    })
    @Operation(summary = "Метод для получения новго access-токена", description = "Позволяет сгенерировать новый access-токен для зарегистрированных пользователей на платформе")
    @PostMapping("/access-token")
    public ResponseEntity<JwtResponseDto> getAccessToken(@RequestBody @Valid RefreshJwtRequestDto refreshToken) throws AuthException {
        JwtResponseDto jwtResponseDto = authService.getAccessToken(refreshToken.getRefreshToken());
        log.info(GET_ACCESS_TOKEN_MESSAGE_LOGGER_CONTROLLER, refreshToken.getRefreshToken());
        return ResponseEntity.ok(jwtResponseDto);
    }

    /**
     * Этот метод позволяет получить новый refresh-токен для зарегистрированного пользователя на платформе
     *
     * @param refreshToken класс-DTO с refresh-токеном, выданный пользователю приложением
     * @return Возвращает ответ с личной информацией о пользователе с новым, сгенерированным refresh-токеном
     * @throws AuthException исключение аутентификации, в случае неправильно введенных личных данных пользователем
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый refresh-токен успешно выдан (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = JwtResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)")
    })
    @Operation(summary = "Метод для получения новго refresh-токена", description = "Позволяет сгенерировать новый refresh-токен для зарегистрированных пользователей на платформе")
    @PostMapping("/refresh-token")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<JwtResponseDto> getRefreshToken(@RequestBody @Valid RefreshJwtRequestDto refreshToken) throws AuthException {
        JwtResponseDto jwtResponseDto = authService.getRefreshToken(refreshToken.getRefreshToken());
        log.info(GET_REFRESH_TOKEN_MESSAGE_LOGGER_CONTROLLER, refreshToken.getRefreshToken());
        return ResponseEntity.ok(jwtResponseDto);
    }
}
