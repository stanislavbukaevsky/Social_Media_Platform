package com.github.stanislavbukaevsky.socialmediaplatform.exception;

/**
 * Класс-исключение, если пользователь забанин. <br>
 * Наследуется от класса {@link RuntimeException}
 */
public class BlockingForbiddenException extends RuntimeException {
    public BlockingForbiddenException(String message) {
        super(message);
    }
}
