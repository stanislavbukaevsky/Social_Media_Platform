package com.github.stanislavbukaevsky.socialmediaplatform.mapper;

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
}
