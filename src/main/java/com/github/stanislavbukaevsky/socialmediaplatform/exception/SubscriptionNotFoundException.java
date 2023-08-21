package com.github.stanislavbukaevsky.socialmediaplatform.exception;

/**
 * Класс-исключение, если подписка не найдена в базе данных. <br>
 * Наследуется от класса {@link RuntimeException}
 */
public class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException(String message) {
        super(message);
    }
}
