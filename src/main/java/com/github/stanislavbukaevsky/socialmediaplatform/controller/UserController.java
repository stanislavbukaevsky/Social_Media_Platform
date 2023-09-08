package com.github.stanislavbukaevsky.socialmediaplatform.controller;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.UserDto;
import com.github.stanislavbukaevsky.socialmediaplatform.service.UserService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.GET_USER_INFO_MESSAGE_LOGGER_CONTROLLER;

/**
 * Класс-контроллер для работы со всеми зарегистрированными пользователями на платформе
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Работа со всеми зарегистрированными пользователями на платформе", description = "Позволяет управлять методами по работе со всеми зарегистрированными пользователями на платформе")
public class UserController {
    private final UserService userService;

    /**
     * Этот метод позволяет получить информацию об авторизированном пользователе на платформе
     *
//     * @param principal пользователь, вошедший в систему
     * @return Возвращает полную информацию о пользователе
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о пользователе успешно получена (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)")
    })
    @Operation(summary = "Метод для просмотра информации об авторизированном пользователе на платформе", description = "Позволяет получить информацию об авторизированном пользователе на платформе")
    @GetMapping("/info-me")
    public ResponseEntity<UserDto> getUserInfo(Principal principal) {
        UserDto user = userService.getUserInfo(principal.getName());
        log.info(GET_USER_INFO_MESSAGE_LOGGER_CONTROLLER);
        return ResponseEntity.ok(user);
    }
}
