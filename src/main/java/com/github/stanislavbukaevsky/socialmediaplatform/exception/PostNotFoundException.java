package com.github.stanislavbukaevsky.socialmediaplatform.exception;

/**
 * Класс-исключение, если пост не найден в базе данных. <br>
 * Наследуется от класса {@link RuntimeException}
 */
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }
}
