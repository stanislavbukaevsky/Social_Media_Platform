package com.github.stanislavbukaevsky.socialmediaplatform.controller;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.BlockingDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.ChangeRoleDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.MessageDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.TextDto;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Blocking;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Role;
import com.github.stanislavbukaevsky.socialmediaplatform.service.AdministrationService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.*;

/**
 * Класс-контроллер для работы с административной частью на платформе
 */
@RestController
@RequestMapping("/administrations")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Работа с методами для модераторов и админов", description = "Позволяет управлять методами для модераторов и админов на платформе")
public class AdministrationController {
    private final AdministrationService administrationService;

    /**
     * Этот метод позволяет отправлять личные сообщения любым пользователям, зарегистрированным на платформе.
     * Метод доступен только пользователям с ролью модератор или админ
     *
     * @param recipientId идентификатор пользователя, получающий личное сообщение
     * @param text        текстовое сообщение
     * @return Возвращает DTO-сообщения
     * @throws AuthException исключение аутентификации, в случае неправильно введенных личных данных пользователя
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Личное сообщение успешно отправлено (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)")
    })
    @Operation(summary = "Метод для отправки личных сообщений любым пользователям", description = "Позволяет отправлять личные сообщения любым пользователям, зарегистрированным на платформе")
    @PostMapping("/message/{recipientId}")
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
    public ResponseEntity<MessageDto> sendMessageAnyUsers(@Parameter(description = "Идентификатор пользователя, кому будет отправлено личное сообщение") @PathVariable Long recipientId, @RequestBody @Valid TextDto text) throws AuthException {
        MessageDto messageDto = administrationService.sendMessageAnyUsers(recipientId, text);
        log.info(SEND_MESSAGE_ANY_USERS_MESSAGE_LOGGER_CONTROLLER, recipientId);
        return ResponseEntity.ok(messageDto);
    }

    /**
     * Этот метод позволяет изменить роль любым пользователям, зарегистрированным на платформе.
     * Метод доступен только пользователям с ролью админ
     *
     * @param recipientId идентификатор пользователя, у которого будет изменена роль
     * @param role        роль пользователя
     * @return Возвращает DTO с информацией об изменении роли пользователя
     * @throws AuthException исключение аутентификации, в случае неправильно введенных личных данных пользователя
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Роль для пользователя успешно изменена (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ChangeRoleDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)")
    })
    @Operation(summary = "Метод для изменения роли любым пользователям", description = "Позволяет изменить роль любым пользователям, зарегистрированным на платформе")
    @PostMapping("/update-role/{recipientId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ChangeRoleDto> changingTheRoleUsers(@Parameter(description = "Идентификатор пользователя, у которого будет изменена роль") @PathVariable Long recipientId, @Parameter(description = "Выбор роли для пользователя") @RequestParam Role role) throws AuthException {
        ChangeRoleDto changeRoleDto = administrationService.changingTheRoleUsers(recipientId, role);
        log.info(CHANGING_THE_ROLE_USERS_MESSAGE_LOGGER_CONTROLLER, recipientId);
        return ResponseEntity.ok(changeRoleDto);
    }

    /**
     * Этот метод позволяет изменить статус блокировки любым пользователям, зарегистрированным на платформе.
     * Метод доступен только пользователям с ролью админ
     *
     * @param recipientId идентификатор пользователя, у которого будет изменен статус блокировки
     * @param blocking    статус блокировки для пользователя
     * @return Возвращает DTO с информацией об изменении статуса блокировки
     * @throws AuthException исключение аутентификации, в случае неправильно введенных личных данных пользователя
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус блокировки для пользователя успешно изменен (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BlockingDto.class))),
            @ApiResponse(responseCode = "400", description = "Неккоректный запрос (Bad Request)"),
            @ApiResponse(responseCode = "401", description = "Неаутентифицированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден (Not Found)")
    })
    @Operation(summary = "Метод для изменения статуса блокировки любым пользователям", description = "Позволяет изменить статус блокировки любым пользователям, зарегистрированным на платформе")
    @PostMapping("/update-blocking/{recipientId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BlockingDto> changingBlockingUsers(@Parameter(description = "Идентификатор пользователя, у которого будет изменен статус блокировки") @PathVariable Long recipientId, @Parameter(description = "Статус блокировки для пользователя") @RequestParam Blocking blocking) throws AuthException {
        BlockingDto blockingDto = administrationService.changingBlockingUsers(recipientId, blocking);
        log.info(CHANGING_BLOCKING_USERS_MESSAGE_LOGGER_CONTROLLER, recipientId);
        return ResponseEntity.ok(blockingDto);
    }
}
