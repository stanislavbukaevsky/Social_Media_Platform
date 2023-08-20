package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import lombok.Data;

import java.util.List;

/**
 * Класс-DTO для поиска поста на платформе
 */
@Data
public class ResponseWrapperPostDto {
    private Integer count;
    private List<PostDto> results;
}
