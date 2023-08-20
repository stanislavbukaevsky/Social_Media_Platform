package com.github.stanislavbukaevsky.socialmediaplatform.controller;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.CorrespondenceDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.MessageDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.TextDto;
import com.github.stanislavbukaevsky.socialmediaplatform.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.REQUEST_FOR_USER_CORRESPONDENCE_MESSAGE_LOGGER_CONTROLLER;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.SEND_MESSAGE_USER_MESSAGE_LOGGER_CONTROLLER;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Работа со всеми сообщениями отправленными пользователями", description = "Позволяет управлять методами по работе со всеми сообщениями отправленными пользователями")
public class MessageController {
    private final MessageService messageService;

    /**
     * Этот метод позволяет отправлять личные сообщения друзьям пользователя
     *
     * @param recipientId идентификатор пользователя, получающий личное сообщение
     * @param text        текстовое сообщение
     * @return Возвращает DTO-сообщения
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Сообщение успешно отправлено пользователю (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)")

    })
    @Operation(summary = "Метод для отправления личных сообщений друзьям пользователя", description = "Позволяет отправлять личные сообщения друзьям пользователя")
    @PostMapping("/send/{recipientId}")
    public ResponseEntity<MessageDto> sendMessageUser(@Parameter(description = "Идентификатор пользователя, получающий личное сообщение") @PathVariable Long recipientId, @RequestBody @Valid TextDto text) {
        log.info(SEND_MESSAGE_USER_MESSAGE_LOGGER_CONTROLLER, recipientId);
        return ResponseEntity.ok(messageService.sendMessageUser(recipientId, text));
    }

    /**
     * Этот метод позволяет получить личную переписку пользователя
     *
     * @param recipientId идентификатор пользователя, с которым запрашивается переписка
     * @return Возвращает список DTO-сообщений
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Список сообщений успешно получен (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CorrespondenceDto.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)")

    })
    @Operation(summary = "Метод для получения личной переписки пользователей", description = "Позволяет получить личную переписку пользователя")
    @GetMapping("/correspondence/{recipientId}")
    public ResponseEntity<List<CorrespondenceDto>> requestForUserCorrespondence(@Parameter(description = "Идентификатор пользователя, с которым запрашивается переписка") @PathVariable Long recipientId) {
        log.info(REQUEST_FOR_USER_CORRESPONDENCE_MESSAGE_LOGGER_CONTROLLER, recipientId);
        return ResponseEntity.ok(messageService.requestForUserCorrespondence(recipientId));
    }
}
