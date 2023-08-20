package com.github.stanislavbukaevsky.socialmediaplatform.service.impl;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.FriendsDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.SubscriptionDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Subscription;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Status;
import com.github.stanislavbukaevsky.socialmediaplatform.exception.SubscriptionNotFoundException;
import com.github.stanislavbukaevsky.socialmediaplatform.exception.UserIdNotFoundException;
import com.github.stanislavbukaevsky.socialmediaplatform.mapper.SubscriptionMapper;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.SubscriptionRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.UserRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.security.UserSecurity;
import com.github.stanislavbukaevsky.socialmediaplatform.service.SubscriptionService;
import com.github.stanislavbukaevsky.socialmediaplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.ExceptionTextMessageConstant.*;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.*;

/**
 * Сервис-класс с бизнес-логикой для всех подписок и заявок в друзья, отправленных на платформе.
 * Реализует интерфейс {@link SubscriptionService}
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserService userService;
    private final UserSecurity userSecurity;

    /**
     * Реализация метода отправки заявки в друзья другому пользователю.
     * С момента отправки заявки, пользователь, отправивший ее, становится подписчиком другого пользователя
     *
     * @param recipientId идентификатор пользователя, получающий заявку в друзья
     * @return Возвращает DTO-подписки на другого пользователя
     */
    @Override
    public SubscriptionDto sendingApplicationFriends(Long recipientId) {
        LocalDateTime dateTime = LocalDateTime.now();
        User senderApp = userService.findUserByUsername(userSecurity.getUsername());
        User recipientApp = userRepository.findById(recipientId).orElseThrow(() ->
                new UserIdNotFoundException(USER_ID_NOT_FOUND_EXCEPTION));

        Subscription subscription = new Subscription();
        subscription.setSenderApplication(senderApp);
        subscription.setRecipientApplication(recipientApp);
        subscription.setCreatedAt(dateTime);
        subscription.setUpdatedAt(dateTime);
        subscription.setStatus(Status.CONSIDERATION);
        if (senderApp.getSubscribers().contains(senderApp)) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION);
        }
        senderApp.getSubscribers().add(recipientApp);
        userRepository.save(senderApp);

        Subscription result = subscriptionRepository.save(subscription);
        SubscriptionDto subscriptionDto = subscriptionMapper.toSubscriptionDto(result);
        subscriptionDto.setDescription(Status.CONSIDERATION.getDescription());
        log.info(SENDING_APPLICATION_FRIENDS_MESSAGE_LOGGER_SERVICE, recipientId);
        return subscriptionDto;
    }

    /**
     * Реализация метода отмены подачи заявки в друзья, соответственно отказ быть подписчиком
     *
     * @param subscriptionId идентификатор подписки
     * @return Возвращает информационную строку отказа от подписки
     */
    @Override
    public String cancelSubscriptionRequest(Long subscriptionId) {
        Subscription subscription = findSubscriptionById(subscriptionId);
        User senderApp = userService.findUserByUsername(userSecurity.getUsername());
        User recipientApp = subscription.getRecipientApplication();
        if (subscription.getStatus() != Status.CONSIDERATION) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_2);
        }

        subscriptionRepository.delete(subscription);
        senderApp.getSubscribers().remove(recipientApp);
        userRepository.save(senderApp);
        log.info(CANCEL_SUBSCRIPTION_REQUEST_MESSAGE_LOGGER_SERVICE, subscriptionId);
        return "Вы отказались от подписки с " + recipientApp;
    }

    /**
     * Реализация метода добавления в друзья другого пользователя (подписчика)
     *
     * @param subscriptionId идентификатор подписки
     * @return Возвращает DTO добавления пользователей в друзья
     */
    @Override
    public FriendsDto addFriendRequest(Long subscriptionId) {
        LocalDateTime dateTime = LocalDateTime.now();
        Subscription subscription = findSubscriptionById(subscriptionId);
        if (subscription.getStatus() != Status.CONSIDERATION) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_2);
        }

        User senderApp = subscription.getSenderApplication();
        User recipientApp = userService.findUserByUsername(userSecurity.getUsername());
        recipientApp.getFriends().add(senderApp);
        senderApp.getFriends().add(recipientApp);
        subscription.setUpdatedAt(dateTime);
        subscription.setStatus(Status.ACCEPTANCE);
        userRepository.save(recipientApp);
        userRepository.save(senderApp);

        Subscription result = subscriptionRepository.save(subscription);
        FriendsDto friendsDto = subscriptionMapper.toFriendsDto(result);
        friendsDto.setDescription(Status.ACCEPTANCE.getDescription());
        log.info(ADD_FRIEND_REQUEST_MESSAGE_LOGGER_SERVICE, subscriptionId);
        return friendsDto;
    }

    /**
     * Реализация метода отклонения заявки пользователя в друзья
     *
     * @param subscriptionId идентификатор подписки
     * @return Возвращает DTO отклонения заявки пользователя в друзья
     */
    @Override
    public FriendsDto rejectFriendRequest(Long subscriptionId) {
        LocalDateTime dateTime = LocalDateTime.now();
        Subscription subscription = findSubscriptionById(subscriptionId);
        User recipientApp = userService.findUserByUsername(userSecurity.getUsername());
        if (subscription.getStatus() != Status.CONSIDERATION || !subscription.getRecipientApplication().equals(recipientApp)) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_2);
        }

        subscription.setUpdatedAt(dateTime);
        subscription.setStatus(Status.REJECTION);

        Subscription result = subscriptionRepository.save(subscription);
        FriendsDto friendsDto = subscriptionMapper.toFriendsDto(result);
        friendsDto.setDescription(Status.REJECTION.getDescription());
        log.info(REJECT_FRIEND_REQUEST_MESSAGE_LOGGER_SERVICE, subscriptionId);
        return friendsDto;
    }

    /**
     * Реализация метода удаления из друзей и из подписок пользователя
     *
     * @param subscriptionId идентификатор подписки
     * @return Возвращает информационную строку отказа от дружбы и подписки
     */
    @Override
    public String deleteFromFriends(Long subscriptionId) {
        Subscription subscription = findSubscriptionById(subscriptionId);
        User senderApp = userService.findUserByUsername(userSecurity.getUsername());
        User recipientApp = subscription.getRecipientApplication();
        if (subscription.getStatus() != Status.ACCEPTANCE) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_2);
        }

        senderApp.getFriends().remove(recipientApp);
        recipientApp.getFriends().remove(senderApp);
        senderApp.getSubscribers().remove(recipientApp);

        userRepository.save(senderApp);
        userRepository.save(recipientApp);
        log.info(DELETE_FROM_FRIENDS_MESSAGE_LOGGER_SERVICE, subscriptionId);
        return "Вы удалили из друзей и из подписок " + recipientApp;
    }

    /**
     * Приватный метод для поиска подписки по ее уникальному идентификатору
     *
     * @param id идентификатор подписки
     * @return Возвращает найденную подписку по ее уникальному идентификатору
     */
    private Subscription findSubscriptionById(Long id) {
        log.info(FIND_SUBSCRIPTION_BY_ID_MESSAGE_LOGGER_SERVICE, id);
        return subscriptionRepository.findSubscriptionById(id).orElseThrow(() ->
                new SubscriptionNotFoundException(SUBSCRIPTION_NOT_FOUND_EXCEPTION));
    }


}
