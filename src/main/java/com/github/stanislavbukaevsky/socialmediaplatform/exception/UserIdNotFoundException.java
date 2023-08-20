package com.github.stanislavbukaevsky.socialmediaplatform.exception;

import java.util.NoSuchElementException;

/**
 * Класс-исключение, если пользователь не найден по идентификатору в базе данных. <br>
 * Наследуется от класса {@link NoSuchElementException}
 */
public class UserIdNotFoundException extends NoSuchElementException {
    public UserIdNotFoundException(String message) {
        super(message);
    }
}
