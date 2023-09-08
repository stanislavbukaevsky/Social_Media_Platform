package com.github.stanislavbukaevsky.socialmediaplatform.service;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.BlockingDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.ChangeRoleDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.MessageDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.TextDto;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Blocking;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Role;

import javax.security.auth.message.AuthException;

/**
 * Сервис-интерфейс для работы с административной частью на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface AdministrationService {
    /**
     * Сигнатура метода для отправки личных сообщений любым пользователям, зарегистрированным на платформе.
     * Метод доступен только пользователям с ролью модератор или админ
     *
     * @param recipientId идентификатор пользователя, получающий личное сообщение
     * @param text        текстовое сообщение
     * @return Возвращает DTO-сообщения
     * @throws AuthException исключение аутентификации, в случае неправильно введенных личных данных пользователя
     */
    MessageDto sendMessageAnyUsers(Long recipientId, TextDto text) throws AuthException;

    /**
     * Сигнатура метода для изменения роли любым пользователям, зарегистрированным на платформе.
     * Метод доступен только пользователям с ролью админ
     *
     * @param recipientId идентификатор пользователя, у которого будет изменена роль
     * @param role        роль пользователя
     * @return Возвращает DTO с информацией об изменении роли пользователя
     * @throws AuthException исключение аутентификации, в случае неправильно введенных личных данных пользователя
     */
    ChangeRoleDto changingTheRoleUsers(Long recipientId, Role role) throws AuthException;

    /**
     * Сигнатура метода для изменения статуса блокировки любым пользователям, зарегистрированным на платформе.
     * Метод доступен только пользователям с ролью админ
     *
     * @param recipientId идентификатор пользователя, у которого будет изменен статус блокировки
     * @param blocking    статус блокировки для пользователя
     * @return Возвращает DTO с информацией об изменении статуса блокировки
     * @throws AuthException исключение аутентификации, в случае неправильно введенных личных данных пользователя
     */
    BlockingDto changingBlockingUsers(Long recipientId, Blocking blocking) throws AuthException;
}
