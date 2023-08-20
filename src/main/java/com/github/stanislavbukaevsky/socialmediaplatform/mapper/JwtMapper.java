package com.github.stanislavbukaevsky.socialmediaplatform.mapper;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.JwtResponseDto;
import org.mapstruct.Mapper;

/**
 * Маппер-интерфейс, который преобразует информацию о пользователе, зарегистрированном на платформе в DTO
 */
@Mapper
public interface JwtMapper {
    /**
     * Этот метод преобразует полученные поля в DTO, для получения токена
     *
     * @param id       идентификатор пользователя
     * @param username логин пользователя
     * @param email    электронная почта
     * @param token    jwt-токен
     * @return Возвращает DTO, содержащую ответ пользователю на запрос
     */
    JwtResponseDto toJwtDto(Long id, String username, String email, String token);
}
