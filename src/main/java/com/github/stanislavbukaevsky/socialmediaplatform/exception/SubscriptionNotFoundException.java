package com.github.stanislavbukaevsky.socialmediaplatform.exception;

import java.util.NoSuchElementException;

/**
 * Класс-исключение, если подписка не найдена в базе данных. <br>
 * Наследуется от класса {@link NoSuchElementException}
 */
public class SubscriptionNotFoundException extends NoSuchElementException {
    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
