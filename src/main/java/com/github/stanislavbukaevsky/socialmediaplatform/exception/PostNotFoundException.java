package com.github.stanislavbukaevsky.socialmediaplatform.exception;

import java.util.NoSuchElementException;

/**
 * Класс-исключение, если пост не найден в базе данных. <br>
 * Наследуется от класса {@link NoSuchElementException}
 */
public class PostNotFoundException extends NoSuchElementException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
