package com.github.stanislavbukaevsky.socialmediaplatform.repository;

import com.github.stanislavbukaevsky.socialmediaplatform.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс-репозиторий для работы с методами всех изображений, опубликованных на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link Image} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    /**
     * Этот метод ищет изображения по идентификатору поста
     *
     * @param id идентификатор поста
     * @return Возвращает найденное изображение по идентификатору поста
     */
    Optional<Image> findImageByPostId(Long id);
}
