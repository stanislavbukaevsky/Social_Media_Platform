package com.github.stanislavbukaevsky.socialmediaplatform.mapper;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.BlockingDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.ChangeRoleDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.UserDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import org.mapstruct.Mapper;

/**
 * Маппер-интерфейс, который преобразует сущность всех пользователей, зарегистрированных на платформе {@link User}
 * в DTO {@link UserDto}
 */
@Mapper
public interface UserMapper {
    /**
     * Этот метод конвертирует сущность пользователей в DTO пользователей.
     *
     * @param user сущность пользователя
     * @return Возвращает сконвертированную DTO пользователя, зарегистрированного на платформе
     */
    UserDto toUserDto(User user);

    /**
     * Этот метод конвертирует сущность пользователей в DTO блокировки пользователей
     *
     * @param user сущность пользователя
     * @return Возвращает сконвертированную DTO блокировки пользователей, зарегистрированного на платформе
     */
    BlockingDto toBlockingDto(User user);

    /**
     * Этот метод конвертирует сущность пользователей в DTO изменения роли пользователя
     *
     * @param user сущность пользователя
     * @return Возвращает сконвертированную DTO изменения роли пользователя, зарегистрированного на платформе
     */
    ChangeRoleDto toChangeRoleDto(User user);
}
