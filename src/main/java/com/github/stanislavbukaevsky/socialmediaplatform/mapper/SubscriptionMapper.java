package com.github.stanislavbukaevsky.socialmediaplatform.mapper;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.FriendsDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.SubscriptionDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер-интерфейс, который преобразует сущность всех подписок, отправленных на платформе {@link Subscription}
 * в DTO {@link SubscriptionDto}
 */
@Mapper
public interface SubscriptionMapper {

    /**
     * Этот метод конвертирует сущность подписки в DTO подписки. <br>
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param subscription сущность подписки
     * @return Возвращает сконвертированную DTO подписки, отправленной на платформе
     */
    @Mapping(source = "subscription.senderApplication.id", target = "senderApp")
    @Mapping(source = "subscription.recipientApplication.id", target = "recipientApp")
    SubscriptionDto toSubscriptionDto(Subscription subscription);

    /**
     * Этот метод конвертирует сущность подписки в DTO заявки в друзья. <br>
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param subscription сущность подписки
     * @return Возвращает сконвертированную DTO заявки в друзья, отправленной на платформе
     */
    @Mapping(source = "subscription.senderApplication.id", target = "senderApp")
    @Mapping(source = "subscription.senderApplication.username", target = "usernameSender")
    @Mapping(source = "subscription.recipientApplication.id", target = "recipientApp")
    @Mapping(source = "subscription.recipientApplication.username", target = "usernameRecipient")
    FriendsDto toFriendsDto(Subscription subscription);

}
