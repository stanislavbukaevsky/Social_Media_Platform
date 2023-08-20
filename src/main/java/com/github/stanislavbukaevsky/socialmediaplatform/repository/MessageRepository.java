package com.github.stanislavbukaevsky.socialmediaplatform.repository;

import com.github.stanislavbukaevsky.socialmediaplatform.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс-репозиторий для работы с методами всех сообщений, опубликованных на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link Message} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Этот метод ищет отправленные сообщение от пользователя
     *
     * @param id идентификатор пользователя
     * @return Возвращает список сообщений от пользователя
     */
    List<Message> findAllByRecipientMessageId(Long id);
}
