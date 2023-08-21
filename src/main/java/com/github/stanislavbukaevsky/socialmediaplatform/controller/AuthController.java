package com.github.stanislavbukaevsky.socialmediaplatform.controller;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.AppErrorDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.JwtRequestDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.RegistrationDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.UserDto;
import com.github.stanislavbukaevsky.socialmediaplatform.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.ExceptionTextMessageConstant.AUTHORIZATION_MESSAGE_EXCEPTION_CONTROLLER;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.*;

/**
 * Класс-контроллер для работы с регистрацией и авторизацией пользователей на платформе
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Работа с регистрацией и авторизацией", description = "Позволяет управлять методами по работе с регистрацией и авторизацией пользователей на платформе")
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    /**
     * Этот метод реализует регистрацию новых пользователей на платформе
     *
     * @param registrationDto класс-DTO для регистрации пользователя на платформе
     * @return Возвращает зарегистрированного пользователя и всю информацию о нем
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый пользователь зарегистрирован (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)")
    })
    @Operation(summary = "Метод регистрации пользователей на платформе", description = "Позволяет зарегистрироваться новому пользователю на платформе")
    @PostMapping("/registration")
    public ResponseEntity<UserDto> registration(@RequestBody @Valid RegistrationDto registrationDto) {
        UserDto user = authService.registration(registrationDto);
        log.info(REGISTRATION_MESSAGE_LOGGER_CONTROLLER, registrationDto.getUsername(), registrationDto.getEmail());
        return ResponseEntity.ok(user);
    }

    /**
     * Этот метод реализует авторизацию пользователей на платформе
     *
     * @param jwtRequestDto класс-DTO для авторизации пользователя на платформе
     * @return Возвращает авторизированного пользователя, если такой существует
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно авторизирован (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = JwtRequestDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)")
    })
    @Operation(summary = "Метод авторизации пользователей на платформе", description = "Позволяет авторизироваться зарегистрированному пользователю на платформе")
    @PostMapping("/authorization")
    public ResponseEntity<?> authorization(@RequestBody @Valid JwtRequestDto jwtRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestDto.getUsername(), jwtRequestDto.getPassword()));
        } catch (BadCredentialsException e) {
            log.debug(AUTHORIZATION_MESSAGE_LOGGER_CONTROLLER_2, jwtRequestDto.getUsername(), jwtRequestDto.getPassword());
            return new ResponseEntity<>(new AppErrorDto(HttpStatus.UNAUTHORIZED.value(), AUTHORIZATION_MESSAGE_EXCEPTION_CONTROLLER), HttpStatus.UNAUTHORIZED);
        }

        log.info(AUTHORIZATION_MESSAGE_LOGGER_CONTROLLER, jwtRequestDto.getUsername());
        return ResponseEntity.ok(authService.authorization(jwtRequestDto));
    }
}
