package com.github.stanislavbukaevsky.socialmediaplatform.service.impl;

import com.github.stanislavbukaevsky.socialmediaplatform.entity.Image;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Post;
import com.github.stanislavbukaevsky.socialmediaplatform.exception.ImageNotFoundException;
import com.github.stanislavbukaevsky.socialmediaplatform.exception.PostNotFoundException;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.ImageRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.repository.PostRepository;
import com.github.stanislavbukaevsky.socialmediaplatform.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static com.github.stanislavbukaevsky.socialmediaplatform.constant.ExceptionTextMessageConstant.IMAGE_NOT_FOUND_EXCEPTION;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.ExceptionTextMessageConstant.POST_NOT_FOUND_EXCEPTION;
import static com.github.stanislavbukaevsky.socialmediaplatform.constant.LoggerTextMessageConstant.*;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Сервис-класс с бизнес-логикой для всех изображений, опубликованных на платформе.
 * Реализует интерфейс {@link ImageService}
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;
    @Value("${path.to.images.folder}")
    private String imageDir;

    /**
     * Реализация метода для поиска изображения по идентификатору поста
     *
     * @param id идентификатор поста
     * @return Возвращает найденное изображение по идентификатору поста
     */
    @Override
    public Image findImageByPostId(Long id) {
        log.info(FIND_IMAGE_BY_POST_ID_MESSAGE_LOGGER_SERVICE, id);
        return imageRepository.findImageByPostId(id).orElseThrow(() ->
                new ImageNotFoundException(IMAGE_NOT_FOUND_EXCEPTION));
    }

    /**
     * Реализация метода для добавления изображения к посту, размещенного на платформе
     *
     * @param id        идентификатор поста
     * @param imageFile файл изображения
     * @return Возвращает добавленное изображение к посту
     * @throws IOException общий класс исключений ввода-вывода
     */
    @Override
    public Image addImagePost(Long id, MultipartFile imageFile) throws IOException {
        LocalDateTime dateTime = LocalDateTime.now();
        Post post = postRepository.findPostById(id).orElseThrow(() ->
                new PostNotFoundException(POST_NOT_FOUND_EXCEPTION));
        Image image = new Image();
        saveImagePost(post.getId(), imageFile, image);
        image.setCreatedAt(dateTime);
        image.setUpdatedAt(dateTime);
        image.setPost(post);
        log.info(ADD_IMAGE_POST_MESSAGE_LOGGER_SERVICE, id);
        return imageRepository.save(image);
    }

    /**
     * Реализация метода для изменения изображения у поста, размещенного на платформе
     *
     * @param id        идентификатор поста
     * @param imageFile файл изображения
     * @return Возвращает измененное изображение у поста
     * @throws IOException общий класс исключений ввода-вывода
     */
    @Override
    public Image updateImagePost(Long id, MultipartFile imageFile) throws IOException {
        LocalDateTime dateTime = LocalDateTime.now();
        Post post = postRepository.findPostById(id).orElseThrow(() ->
                new PostNotFoundException(POST_NOT_FOUND_EXCEPTION));
        Image image = findImageByPostId(post.getId());
        saveImagePost(post.getId(), imageFile, image);
        image.setUpdatedAt(dateTime);
        image.setPost(post);
        log.info(UPDATE_IMAGE_POST_MESSAGE_LOGGER_SERVICE, id);
        return imageRepository.save(image);
    }

    /**
     * Реализация метода для получения изображения у поста по его идентификатору
     *
     * @param id идентификатор поста
     * @return Возвращает массив байт искомого изображения
     */
    @Override
    public byte[] getPostImage(Long id) {
        Image image = findImageByPostId(id);
        log.info(GET_POST_IMAGE_MESSAGE_LOGGER_SERVICE, id);
        return image.getData();
    }

    /**
     * Приватный метод для добавления изображения к постам на платформе
     *
     * @param id        идентификатор поста
     * @param imageFile файл изображения
     * @param image     сущность изображения
     * @throws IOException общий класс исключений ввода-вывода
     */
    private void saveImagePost(Long id, MultipartFile imageFile, Image image) throws IOException {
        Path path = Path.of(imageDir, id + "." + getExtensions(imageFile.getOriginalFilename()));
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        try (InputStream is = imageFile.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        image.setFilePath(path.toString());
        image.setFileSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());
        image.setData(imageFile.getBytes());
        log.info(SAVE_IMAGE_POST_MESSAGE_LOGGER_SERVICE, id);
    }

    /**
     * Приватный метод, генерирующий расширение файла
     *
     * @param fileName название файла
     * @return Возвращает сгенерированное расширение файла
     */
    private String getExtensions(String fileName) {
        log.info(GET_EXTENSIONS_MESSAGE_LOGGER_SERVICE, fileName);
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
