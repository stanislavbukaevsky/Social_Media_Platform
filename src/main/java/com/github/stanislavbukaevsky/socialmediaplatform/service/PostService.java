package com.github.stanislavbukaevsky.socialmediaplatform.service;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.FeedActivityDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.PostCreatedDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.PostDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.ResponseWrapperPostDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Сервис-интерфейс для всех постов, опубликованных на платформе.
 * В этом интерфейсе прописана только сигнатура методов без реализации
 */
public interface PostService {

    /**
     * Сигнатура метода для поиска поста по его идентификатору
     *
     * @param id идентификатор поста
     * @return Возвращает найденный пост по его идентификатору
     */
    Post findPostById(Long id);

    /**
     * Сигнатура метода для добавления новых постов на платформу
     *
     * @param postCreatedDto добавляемый пост
     * @param imageFile      изображение
     * @return Возвращает новый, добавленный DTO-пост с изображением на платформу
     * @throws IOException общий класс исключений ввода-вывода
     */
    PostDto addPost(PostCreatedDto postCreatedDto, MultipartFile imageFile) throws IOException;

    /**
     * Сигнатура метода для получения и просмотра всех постов, опубликованных на платформе
     *
     * @param title зоголовок поста
     * @return Возвращает DTO всех опубликованных постов на платформе
     */
    ResponseWrapperPostDto getAllPost(String title);

    /**
     * Сигнатура метода для получения изображения у поста по его идентификатору
     *
     * @param id идентификатор поста
     * @return Возвращает массив байт искомого изображения
     */
    byte[] getPostImage(Long id);

    /**
     * Сигнатура метода для измения информации о посте, размещенного на платформе
     *
     * @param id             идентификатор изменяемого поста
     * @param postCreatedDto добавленный пост
     * @return Возвращает DTO измененного поста на платформе
     */
    PostDto updatePost(Long id, PostCreatedDto postCreatedDto);

    /**
     * Сигнатура метода для изменения изображения у поста, размещенного на платформе
     *
     * @param id        идентификатор поста
     * @param imageFile изображение
     * @return Возвращает DTO измененного изображения у поста, размещенного на платформе
     * @throws IOException общий класс исключений ввода-вывода
     */
    PostDto updatePostImage(Long id, MultipartFile imageFile) throws IOException;

    /**
     * Сигнатура метода для удаления поста с платформы по его идентификатору
     *
     * @param id идентификатор удаляемого поста
     */
    void deletePost(Long id);

    /**
     * Сигнатура метода для поиска постов от пользователей, на которых он подписан.
     * Также, метод поддерживает пагинацию и сортировку по времени создания постов
     *
     * @param page номер страницы
     * @param size количество постов на странице
     * @return Возвращает сконвертированную DTO ленты активности пользователя
     */
    List<FeedActivityDto> userActivityFeed(int page, int size);
}
