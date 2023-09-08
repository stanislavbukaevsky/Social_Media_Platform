package com.github.stanislavbukaevsky.socialmediaplatform.service.impl;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.CorrespondenceDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.MessageDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.TextDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Message;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Blocking;
import com.github.stanislavbukaevsky.socialmediaplatform.exception.UserIdNotFoundException;
import com.github.stanislavbukaevsky.socialmediaplatform.mapper.MessageMapper;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.MessageRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.UserRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.security.UserSecurity;
import com.github.stanislavbukaevsky.socialmediaplatform.service.MessageService;
import com.github.stanislavbukaevsky.socialmediaplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.ExceptionTextMessageConstant.*;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.REQUEST_FOR_USER_CORRESPONDENCE_MESSAGE_LOGGER_SERVICE;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.SEND_MESSAGE_USER_MESSAGE_LOGGER_SERVICE;

/**
 * Сервис-класс с бизнес-логикой для всех сообщений, опубликованных на платформе.
 * Реализует интерфейс {@link MessageService}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;
    private final UserService userService;
    private final UserSecurity userSecurity;

    /**
     * Реализация метода для отправки личных сообщений друзьям пользователя
     *
     * @param recipientId идентификатор пользователя, получающий личное сообщение
     * @param text        текстовое сообщение
     * @return Возвращает DTO-сообщения
     */
    @Override
    public MessageDto sendMessageUser(Long recipientId, TextDto text) {
        User senderUser = userService.findUserByUsername(userSecurity.getUsername());
        User recipientUser = userRepository.findById(recipientId).orElseThrow(() ->
                new UserIdNotFoundException(USER_ID_NOT_FOUND_EXCEPTION));

        if (senderUser.getFriends().contains(recipientUser) && senderUser.getBlocking().equals(Blocking.NOT_BAN)) {
            MessageDto messageDto = addMessageDto(text, senderUser, recipientUser);
            messageDto.setDescription("Пользователь " + senderUser.getUsername() + " отправил личное сообщение пользователю " + recipientUser.getUsername());
            log.info(SEND_MESSAGE_USER_MESSAGE_LOGGER_SERVICE, recipientId);
            return messageDto;
        }
        throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_3);
    }

    /**
     * Реализация метода для запроса личной переписки пользователей
     *
     * @param recipientId идентификатор пользователя, с которым запрашивается переписка
     * @return Возвращает список DTO-сообщений
     */
    @Override
    public List<CorrespondenceDto> requestForUserCorrespondence(Long recipientId) {
        User senderUser = userService.findUserByUsername(userSecurity.getUsername());
        User recipientUser = userRepository.findById(recipientId).orElseThrow(() ->
                new UserIdNotFoundException(USER_ID_NOT_FOUND_EXCEPTION));
        List<Message> messages = messageRepository.findAllByRecipientMessageId(recipientId);
        if (!senderUser.getFriends().contains(recipientUser)) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_3);
        }

        List<CorrespondenceDto> correspondencesDto = new ArrayList<>();
        for (Message message : messages) {
            CorrespondenceDto correspondenceDto = new CorrespondenceDto();
            correspondenceDto.setId(message.getId());
            correspondenceDto.setUsernameSender(message.getSenderMessage().getUsername());
            correspondenceDto.setUsernameRecipient(message.getRecipientMessage().getUsername());
            correspondenceDto.setText(message.getText());
            correspondenceDto.setDescription("Личная переписка пользователя " + senderUser.getUsername() + " с пользователем " + recipientUser.getUsername());
            correspondenceDto.setDateTime(message.getCreatedAt());
            correspondencesDto.add(correspondenceDto);
        }
        log.info(REQUEST_FOR_USER_CORRESPONDENCE_MESSAGE_LOGGER_SERVICE, recipientId);
        return correspondencesDto;
    }

    /**
     * Реализация метода для создания нового личного сообщения
     *
     * @param text          текст сообщения
     * @param senderUser    пользователь, отправляющий сообщение
     * @param recipientUser пользователь, получающий сообщение
     * @return Возвращает DTO готового личного сообщения
     */
    @Override
    public MessageDto addMessageDto(TextDto text, User senderUser, User recipientUser) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (!recipientUser.getId().equals(senderUser.getId())) {
            Message message = new Message();
            message.setText(text.getText());
            message.setSenderMessage(senderUser);
            message.setRecipientMessage(recipientUser);
            message.setCreatedAt(dateTime);

            Message result = messageRepository.save(message);
            return messageMapper.toMessageDto(result);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, RESPONSE_STATUS_EXCEPTION_2);
    }
}
