package com.github.stanislavbukaevsky.socialmediaplatform.exception;

/**
 * Класс-исключение, если изображение не найдено в базе данных. <br>
 * Наследуется от класса {@link RuntimeException}
 */
public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(String message) {
        super(message);
    }
}
