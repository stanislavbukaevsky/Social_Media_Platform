package com.github.stanislavbukaevsky.socialmediaplatform.exception;

/**
 * Класс-исключение, если не найдены посты от друзей в базе данных. <br>
 * Наследуется от класса {@link RuntimeException}
 */
public class ActivityFeedNotFoundException extends RuntimeException {
    public ActivityFeedNotFoundException(String message) {
        super(message);
    }
}
