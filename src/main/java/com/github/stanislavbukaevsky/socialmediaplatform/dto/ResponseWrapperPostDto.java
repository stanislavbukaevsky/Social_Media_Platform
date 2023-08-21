package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Класс-DTO для поиска поста на платформе
 */
@Data
public class ResponseWrapperPostDto {
    @Schema(description = "Количество постов")
    private Integer count;
    @Schema(description = "Список постов с информацией о них")
    private List<PostDto> results;
}
