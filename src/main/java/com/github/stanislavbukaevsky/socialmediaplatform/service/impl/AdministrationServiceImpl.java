package com.github.stanislavbukaevsky.socialmediaplatform.service.impl;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.BlockingDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.ChangeRoleDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.MessageDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.TextDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Blocking;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Role;
import com.github.stanislavbukaevsky.socialmediaplatform.exception.UserIdNotFoundException;
import com.github.stanislavbukaevsky.socialmediaplatform.mapper.UserMapper;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.UserRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.security.UserSecurity;
import com.github.stanislavbukaevsky.socialmediaplatform.service.AdministrationService;
import com.github.stanislavbukaevsky.socialmediaplatform.service.MessageService;
import com.github.stanislavbukaevsky.socialmediaplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.time.LocalDateTime;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.ExceptionTextMessageConstant.*;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для работы с административной частью на платформе.
 * Реализует интерфейс {@link AdministrationService}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AdministrationServiceImpl implements AdministrationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserSecurity userSecurity;
    private final UserMapper userMapper;
    private final MessageService messageService;

    /**
     * Реализация метода для отправки личных сообщений любым пользователям, зарегистрированным на платформе.
     * Метод доступен только пользователям с ролью модератор или админ
     *
     * @param recipientId идентификатор пользователя, получающий личное сообщение
     * @param text        текстовое сообщение
     * @return Возвращает DTO-сообщения
     * @throws AuthException исключение аутентификации, в случае неправильно введенных личных данных пользователя
     */
    @Override
    public MessageDto sendMessageAnyUsers(Long recipientId, TextDto text) throws AuthException {
        User senderUser = userService.findUserByUsername(userSecurity.getUsername());
        User recipientUser = userRepository.findById(recipientId).orElseThrow(() ->
                new UserIdNotFoundException(USER_ID_NOT_FOUND_EXCEPTION));

        if (senderUser.getRole().equals(Role.MODERATOR) ||
                senderUser.getRole().equals(Role.ADMIN) && senderUser.getBlocking().equals(Blocking.NOT_BAN)) {
            MessageDto messageDto = messageService.addMessageDto(text, senderUser, recipientUser);
            if (senderUser.getRole().equals(Role.MODERATOR)) {
                messageDto.setDescription("Модератор " + senderUser.getUsername() + " отправил личное сообщение пользователю " + recipientUser.getUsername());
                log.info(SEND_MESSAGE_ANY_USERS_MESSAGE_LOGGER_SERVICE, recipientId);
                return messageDto;
            }
            if (senderUser.getRole().equals(Role.ADMIN)) {
                messageDto.setDescription("Администратор " + senderUser.getUsername() + " отправил личное сообщение пользователю " + recipientUser.getUsername());
                log.info(SEND_MESSAGE_ANY_USERS_MESSAGE_LOGGER_SERVICE, recipientId);
                return messageDto;
            }
        }
        log.error(SEND_MESSAGE_ANY_USERS_ERROR_LOGGER_SERVICE, recipientId);
        throw new AuthException(AUTH_EXCEPTION);
    }

    /**
     * Реализация метода для изменения роли любым пользователям, зарегистрированным на платформе.
     * Метод доступен только пользователям с ролью админ
     *
     * @param recipientId идентификатор пользователя, у которого будет изменена роль
     * @param role        роль пользователя
     * @return Возвращает DTO с информацией об изменении роли пользователя
     * @throws AuthException исключение аутентификации, в случае неправильно введенных личных данных пользователя
     */
    @Override
    public ChangeRoleDto changingTheRoleUsers(Long recipientId, Role role) throws AuthException {
        User senderUser = userService.findUserByUsername(userSecurity.getUsername());
        User recipientUser = userRepository.findById(recipientId).orElseThrow(() ->
                new UserIdNotFoundException(USER_ID_NOT_FOUND_EXCEPTION));

        if (senderUser.getRole().equals(Role.ADMIN)) {
            if (role.equals(Role.USER) && !recipientUser.getRole().equals(Role.USER)) {
                recipientUser.setRole(Role.USER);
                ChangeRoleDto changeRoleDto = getGeneratingChangeRoleDto(senderUser, recipientUser);
                changeRoleDto.setDescription("Администратор " + senderUser.getUsername() + " изменил роль для пользователя " + changeRoleDto.getRecipientUsername() + " на USER.");
                log.info(CHANGING_THE_ROLE_USERS_MESSAGE_LOGGER_SERVICE, recipientId);
                return changeRoleDto;
            } else if (role.equals(Role.MODERATOR) && !recipientUser.getRole().equals(Role.MODERATOR)) {
                recipientUser.setRole(Role.MODERATOR);
                ChangeRoleDto changeRoleDto = getGeneratingChangeRoleDto(senderUser, recipientUser);
                changeRoleDto.setDescription("Администратор " + senderUser.getUsername() + " изменил роль для пользователя " + changeRoleDto.getRecipientUsername() + " на MODERATOR. Поздравляем!");
                log.info(CHANGING_THE_ROLE_USERS_MESSAGE_LOGGER_SERVICE, recipientId);
                return changeRoleDto;
            } else if (role.equals(Role.ADMIN) && !recipientUser.getRole().equals(Role.ADMIN)) {
                recipientUser.setRole(Role.ADMIN);
                ChangeRoleDto changeRoleDto = getGeneratingChangeRoleDto(senderUser, recipientUser);
                changeRoleDto.setDescription("Администратор " + senderUser.getUsername() + " изменил роль для пользователя " + changeRoleDto.getRecipientUsername() + " на ADMIN. Поздравляем!");
                log.info(CHANGING_THE_ROLE_USERS_MESSAGE_LOGGER_SERVICE, recipientId);
                return changeRoleDto;
            }
            log.error(CHANGING_THE_ROLE_USERS_ERROR_LOGGER_SERVICE, recipientId);
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_4);
        }
        log.error(CHANGING_THE_ROLE_USERS_ERROR_LOGGER_SERVICE, recipientId);
        throw new AuthException(AUTH_EXCEPTION);
    }

    /**
     * Реализация метода для изменения статуса блокировки любым пользователям, зарегистрированным на платформе.
     * Метод доступен только пользователям с ролью админ
     *
     * @param recipientId идентификатор пользователя, у которого будет изменен статус блокировки
     * @param blocking    статус блокировки для пользователя
     * @return Возвращает DTO с информацией об изменении статуса блокировки
     * @throws AuthException исключение аутентификации, в случае неправильно введенных личных данных пользователя
     */
    @Override
    public BlockingDto changingBlockingUsers(Long recipientId, Blocking blocking) throws AuthException {
        User senderUser = userService.findUserByUsername(userSecurity.getUsername());
        User recipientUser = userRepository.findById(recipientId).orElseThrow(() ->
                new UserIdNotFoundException(USER_ID_NOT_FOUND_EXCEPTION));

        if (senderUser.getRole().equals(Role.ADMIN)) {
            if (blocking.equals(Blocking.BAN) && !recipientUser.getBlocking().equals(Blocking.BAN)) {
                recipientUser.setBlocking(Blocking.BAN);
                BlockingDto blockingDto = getGeneratingBlockingDto(senderUser, recipientUser);
                blockingDto.setDescription("Администратор " + senderUser.getUsername() + " заблокировал и запретил совершать любые действия на площадке, для пользователя " + blockingDto.getRecipientUsername());
                log.info(CHANGING_BLOCKING_USERS_MESSAGE_LOGGER_SERVICE, recipientId);
                return blockingDto;
            } else if (blocking.equals(Blocking.NOT_BAN) && !recipientUser.getBlocking().equals(Blocking.NOT_BAN)) {
                recipientUser.setBlocking(Blocking.NOT_BAN);
                BlockingDto blockingDto = getGeneratingBlockingDto(senderUser, recipientUser);
                blockingDto.setDescription("Администратор " + senderUser.getUsername() + " разблокировал и разрешил совершать действия на площадке, для пользователя " + blockingDto.getRecipientUsername());
                log.info(CHANGING_BLOCKING_USERS_MESSAGE_LOGGER_SERVICE, recipientId);
                return blockingDto;
            }
            log.error(CHANGING_BLOCKING_USERS_ERROR_LOGGER_SERVICE, recipientId);
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_4);
        }
        log.error(CHANGING_BLOCKING_USERS_ERROR_LOGGER_SERVICE, recipientId);
        throw new AuthException(AUTH_EXCEPTION);
    }

    /**
     * Приватный метод для генерации DTO-изменении роли пользователя
     *
     * @param senderUser    пользователь, который меняет роль для пользователя
     * @param recipientUser пользователь, которому меняют роль
     * @return Возвращает DTO с информацией об изменении роли пользователя
     */
    private ChangeRoleDto getGeneratingChangeRoleDto(User senderUser, User recipientUser) {
        LocalDateTime dateTime = LocalDateTime.now();
        recipientUser.setUpdatedAt(dateTime);
        User result = userRepository.save(recipientUser);
        ChangeRoleDto changeRoleDto = userMapper.toChangeRoleDto(result);
        changeRoleDto.setSenderUsername(senderUser.getUsername());
        changeRoleDto.setRecipientUsername(result.getUsername());
        changeRoleDto.setDateTime(result.getUpdatedAt());

        return changeRoleDto;
    }

    /**
     * Приватный метод для генерации DTO-изменении блокировки пользователей
     *
     * @param senderUser    пользователь, который изменяет статус блокировки
     * @param recipientUser пользователь, которому изменяют статус блокировки
     * @return Возвращает DTO с информацией о блокировки пользователей
     */
    private BlockingDto getGeneratingBlockingDto(User senderUser, User recipientUser) {
        LocalDateTime dateTime = LocalDateTime.now();
        recipientUser.setUpdatedAt(dateTime);
        User result = userRepository.save(recipientUser);
        BlockingDto blockingDto = userMapper.toBlockingDto(result);
        blockingDto.setSenderUsername(senderUser.getUsername());
        blockingDto.setRecipientUsername(result.getUsername());
        blockingDto.setDateTime(result.getUpdatedAt());

        return blockingDto;
    }
}
