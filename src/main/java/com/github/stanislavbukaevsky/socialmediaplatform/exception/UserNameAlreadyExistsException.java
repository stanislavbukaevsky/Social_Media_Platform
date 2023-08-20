package com.github.stanislavbukaevsky.socialmediaplatform.exception;

/**
 * Класс-исключение, если логин (имя пользователя) присутствует в базе данных. <br>
 * Наследуется от класса {@link RuntimeException}
 */
public class UserNameAlreadyExistsException extends RuntimeException {
    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
