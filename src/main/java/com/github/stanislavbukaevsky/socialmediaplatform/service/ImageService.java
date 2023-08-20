package com.github.stanislavbukaevsky.socialmediaplatform.service;

import com.github.stanislavbukaevsky.socialmediaplatform.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Сервис-интерфейс для всех изображений, опубликованных на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface ImageService {

    /**
     * Сигнатура метода для поиска изображения по идентификатору поста
     *
     * @param id идентификатор поста
     * @return Возвращает найденное изображение по идентификатору поста
     */
    Image findImageByPostId(Long id);

    /**
     * Сигнатура метода для добавления изображения к посту, размещенного на платформе
     *
     * @param id        идентификатор поста
     * @param imageFile файл изображения
     * @return Возвращает добавленное изображение к посту
     * @throws IOException общий класс исключений ввода-вывода
     */
    Image addImagePost(Long id, MultipartFile imageFile) throws IOException;

    /**
     * Сигнатура метода для изменения изображения у поста, размещенного на платформе
     *
     * @param id        идентификатор поста
     * @param imageFile файл изображения
     * @return Возвращает измененное изображение у поста
     * @throws IOException общий класс исключений ввода-вывода
     */
    Image updateImagePost(Long id, MultipartFile imageFile) throws IOException;

    /**
     * Сигнатура метода для получения изображения у поста по его идентификатору
     *
     * @param id идентификатор поста
     * @return Возвращает массив байт искомого изображения
     */
    byte[] getPostImage(Long id);
}
