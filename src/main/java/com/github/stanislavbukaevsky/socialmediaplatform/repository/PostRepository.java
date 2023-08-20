package com.github.stanislavbukaevsky.socialmediaplatform.repository;

import com.github.stanislavbukaevsky.socialmediaplatform.entity.Post;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Интерфейс-репозиторий для работы с методами всех постов, опубликованных на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link Post} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Этот метод ищет пост по его идентификатору
     *
     * @param id идентификатор поста
     * @return Возвращает найденный пост, у которого совпадает идентификатор с искомым
     */
    Optional<Post> findPostById(Long id);

    /**
     * Этот метод ищет все посты по его названию игнорируя регистр. <br>
     * В этом методе используется аннотация {@link Query}, для детального поиска по постам.
     * Также, используется оператор Like, для обширного поиска
     *
     * @param title название поста
     * @return Возвращает список найденных по названию постов
     */
    @Query(value = "SELECT * FROM posts WHERE lower(title) LIKE lower(concat('%', ?1,'%'))", nativeQuery = true)
    List<Post> findAllByPost(String title);

    /**
     * Этот метод ищет посты от пользователей-друзей в выбранном порядке
     *
     * @param users    коллекция пользователей
     * @param pageable класс с пагинацией
     * @return Возвращает список найденных постов
     */
    List<Post> findByUserInOrderByCreatedAtDesc(Set<User> users, Pageable pageable);
}
