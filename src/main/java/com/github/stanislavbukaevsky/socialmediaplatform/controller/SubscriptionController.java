package com.github.stanislavbukaevsky.socialmediaplatform.controller;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.FriendsDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.SubscriptionDto;
import com.github.stanislavbukaevsky.socialmediaplatform.service.SubscriptionService;
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

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.*;


/**
 * Класс-контроллер для работы со всеми подписками и заявками в друзья, отправленные на платформе
 */
@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Работа со всеми подписками отправленными на платформе", description = "Позволяет управлять методами по работе со всеми подписками и заявками в друзья отправленными на платформе")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    /**
     * Этот метод позволяет отправлять заявки в друзья другому пользователю.
     * С момента отправки заявки, пользователь, отправивший ее, становится подписчиком другого пользователя
     *
     * @param recipientId идентификатор пользователя, получающий заявку в друзья
     * @return Возвращает DTO-подписки на другого пользователя
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка в друзья успешно отправлена (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SubscriptionDto.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Такого пользователя на платформе нет (Not Found)")
    })
    @Operation(summary = "Метод для отправки заявки в друзья по идентификатору другого пользователя", description = "Позволяет отправить заявку в друзья по идентификатору другого пользователя")
    @PostMapping("/sending/{recipientId}")
    public ResponseEntity<SubscriptionDto> sendingApplicationFriends(@Parameter(description = "Идентификатор пользователя, кому отправляется заявка в друзья") @PathVariable Long recipientId) {
        log.info(SENDING_APPLICATION_FRIENDS_MESSAGE_LOGGER_CONTROLLER, recipientId);
        return ResponseEntity.ok(subscriptionService.sendingApplicationFriends(recipientId));

    }

    /**
     * Этот метод позволяет отменить подачу заявки в друзья, соответственно отказаться быть подписчиком другого пользователя
     *
     * @param subscriptionId идентификатор подписки
     * @return Возвращает информационную строку отказа от подписки
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка в друзья успешно отклонена (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Такого пользователя на платформе нет (Not Found)")
    })
    @Operation(summary = "Метод для отмены подачи заявки в друзья, соответственно отказ быть подписчиком другого пользователя", description = "Позволяет отменить подачу заявки в друзья, соответственно отказаться быть подписчиком другого пользователя")
    @PostMapping("/cancel/{subscriptionId}")
    public ResponseEntity<String> cancelSubscriptionRequest(@Parameter(description = "Идентификатор подписки") @PathVariable Long subscriptionId) {
        log.info(CANCEL_SUBSCRIPTION_REQUEST_MESSAGE_LOGGER_CONTROLLER, subscriptionId);
        return ResponseEntity.ok(subscriptionService.cancelSubscriptionRequest(subscriptionId));
    }

    /**
     * Этот метод позволяет добавить в друзья другого пользователя (подписчика)
     *
     * @param subscriptionId идентификатор подписки
     * @return Возвращает DTO добавления пользователей в друзья
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка в друзья успешно принята (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FriendsDto.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Такого пользователя на платформе нет (Not Found)")
    })
    @Operation(summary = "Метод для добавления в друзья другого пользователя (подписчика)", description = "Позволяет добавить в друзья другого пользователя (подписчика)")
    @PostMapping("/add/{subscriptionId}")
    public ResponseEntity<FriendsDto> addFriendRequest(@Parameter(description = "Идентификатор подписки") @PathVariable Long subscriptionId) {
        log.info(ADD_FRIEND_REQUEST_MESSAGE_LOGGER_CONTROLLER, subscriptionId);
        return ResponseEntity.ok(subscriptionService.addFriendRequest(subscriptionId));
    }

    /**
     * Этот метод позволяет отклонить заявку пользователя в друзья
     *
     * @param subscriptionId идентификатор подписки
     * @return Возвращает DTO отклонения заявки пользователя в друзья
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заявка в друзья успешно отклонена (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FriendsDto.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Такого пользователя на платформе нет (Not Found)")
    })
    @Operation(summary = "Метод для отклонения заявки в друзья от другого пользователя", description = "Позволяет отклонить заявку пользователя в друзья")
    @PostMapping("/reject/{subscriptionId}")
    public ResponseEntity<FriendsDto> rejectFriendRequest(@Parameter(description = "Идентификатор подписки") @PathVariable Long subscriptionId) {
        log.info(REJECT_FRIEND_REQUEST_MESSAGE_LOGGER_CONTROLLER, subscriptionId);
        return ResponseEntity.ok(subscriptionService.rejectFriendRequest(subscriptionId));
    }

    /**
     * Этот метод позволяет удалить из друзей и из подписок пользователя
     *
     * @param subscriptionId идентификатор подписки
     * @return Возвращает информационную строку отказа от дружбы и подписки
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно удален из друзей и подписки (OK)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Неавторизированный пользователь (Unauthorized)"),
            @ApiResponse(responseCode = "403", description = "Пользователю запрещен вход на этот ресурс (Forbidden)"),
            @ApiResponse(responseCode = "404", description = "Такого пользователя на платформе нет (Not Found)")
    })
    @Operation(summary = "Метод для удаления пользователя из друзей и подписки", description = "Позволяет удалить из друзей и из подписок пользователя")
    @DeleteMapping("/{subscriptionId}")
    public ResponseEntity<String> deleteFromFriends(@Parameter(description = "Идентификатор подписки") @PathVariable Long subscriptionId) {
        log.info(DELETE_FROM_FRIENDS_MESSAGE_LOGGER_CONTROLLER, subscriptionId);
        return ResponseEntity.ok(subscriptionService.deleteFromFriends(subscriptionId));
    }

}
