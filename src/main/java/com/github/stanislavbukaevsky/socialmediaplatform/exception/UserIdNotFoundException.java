package com.github.stanislavbukaevsky.socialmediaplatform.exception;

/**
 * Класс-исключение, если пользователь не найден по идентификатору в базе данных. <br>
 * Наследуется от класса {@link RuntimeException}
 */
public class UserIdNotFoundException extends RuntimeException {
    public UserIdNotFoundException(String message) {
        super(message);
    }
}
