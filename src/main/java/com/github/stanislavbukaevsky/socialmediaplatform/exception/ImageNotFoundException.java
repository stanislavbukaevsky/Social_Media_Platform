package com.github.stanislavbukaevsky.socialmediaplatform.exception;

import java.util.NoSuchElementException;

/**
 * Класс-исключение, если изображение не найдено в базе данных. <br>
 * Наследуется от класса {@link NoSuchElementException}
 */
public class ImageNotFoundException extends NoSuchElementException {

    public ImageNotFoundException(String message) {
        super(message);
    }
}
