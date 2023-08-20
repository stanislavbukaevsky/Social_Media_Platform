package com.github.stanislavbukaevsky.socialmediaplatform.repository;

import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс-репозиторий для работы с методами всех пользователей, зарегистрированных на платформе.
 * Наследуется от интерфейса {@link JpaRepository}. Параметры: <br>
 * {@link User} - класс-сущность <br>
 * {@link Long} - идентификатор <br>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Этот метод ищет пользователей по его логину
     *
     * @param username имя пользователя (логин)
     * @return Возвращает найденного по логину пользователя
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Этот метод проверяет, есть ли уже имя пользователя и электронная почта в базе данных
     *
     * @param username имя пользователя (логин)
     * @param email    электронная почта
     * @return Возвращает true, если пользователь уже существует в базе данных или false, если его нет
     */
    Boolean existsUserByUsernameAndEmail(String username, String email);
}
