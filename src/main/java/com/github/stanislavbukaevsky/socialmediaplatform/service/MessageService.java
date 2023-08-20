package com.github.stanislavbukaevsky.socialmediaplatform.service;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.CorrespondenceDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.MessageDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.TextDto;

import java.util.List;

/**
 * Сервис-интерфейс для всех сообщений, опубликованных на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface MessageService {

    /**
     * Сигнатура метода для отправки личных сообщений друзьям пользователя
     *
     * @param recipientId идентификатор пользователя, получающий личное сообщение
     * @param text        текстовое сообщение
     * @return Возвращает DTO-сообщения
     */
    MessageDto sendMessageUser(Long recipientId, TextDto text);

    /**
     * Сигнатура метода для запроса личной переписки пользователей
     *
     * @param recipientId идентификатор пользователя, с которым запрашивается переписка
     * @return Возвращает список DTO-сообщений
     */
    List<CorrespondenceDto> requestForUserCorrespondence(Long recipientId);
}
