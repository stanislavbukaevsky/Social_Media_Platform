package com.github.stanislavbukaevsky.socialmediaplatform.repository;

import com.github.stanislavbukaevsky.socialmediaplatform.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс-репозиторий для работы с методами всех подписок и заявок в друзья, отправленных на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link Subscription} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Этот метод ищет подписку по ее уникальному идентификатору
     *
     * @param id идентификатор подписки
     * @return Возвращает найденную подписку по ее уникальному идентификатору
     */
    Optional<Subscription> findSubscriptionById(Long id);
}
