package com.github.stanislavbukaevsky.socialmediaplatform.exception;

import java.util.NoSuchElementException;

/**
 * Класс-исключение, если не найдены посты от друзей в базе данных. <br>
 * Наследуется от класса {@link NoSuchElementException}
 */
public class ActivityFeedNotFoundException extends NoSuchElementException {
    public ActivityFeedNotFoundException(String message) {
        super(message);
    }
}
