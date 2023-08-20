package com.github.stanislavbukaevsky.socialmediaplatform.service;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.FriendsDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.SubscriptionDto;

/**
 * Сервис-интерфейс для всех подписок и заявок в друзья, отправленных на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface SubscriptionService {

    /**
     * Сигнатура метода отправки заявки в друзья другому пользователю.
     * С момента отправки заявки, пользователь, отправивший ее, становится подписчиком другого пользователя
     *
     * @param recipientId идентификатор пользователя, получающий заявку в друзья
     * @return Возвращает DTO-подписки на другого пользователя
     */
    SubscriptionDto sendingApplicationFriends(Long recipientId);

    /**
     * Сигнатура метода отмены подачи заявки в друзья, соответственно отказ быть подписчиком
     *
     * @param subscriptionId идентификатор подписки
     * @return Возвращает информационную строку отказа от подписки
     */
    String cancelSubscriptionRequest(Long subscriptionId);

    /**
     * Сигнатура метода добавления в друзья другого пользователя (подписчика)
     *
     * @param subscriptionId идентификатор подписки
     * @return Возвращает DTO добавления пользователей в друзья
     */
    FriendsDto addFriendRequest(Long subscriptionId);

    /**
     * Сигнатура метода отклонения заявки пользователя в друзья
     *
     * @param subscriptionId идентификатор подписки
     * @return Возвращает DTO отклонения заявки пользователя в друзья
     */
    FriendsDto rejectFriendRequest(Long subscriptionId);

    /**
     * Сигнатура метода удаления из друзей и из подписок пользователя
     *
     * @param subscriptionId идентификатор подписки
     * @return Возвращает информационную строку отказа от дружбы и подписки
     */
    String deleteFromFriends(Long subscriptionId);

}
