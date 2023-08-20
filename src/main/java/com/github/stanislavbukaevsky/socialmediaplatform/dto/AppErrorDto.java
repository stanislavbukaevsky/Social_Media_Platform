package com.github.stanislavbukaevsky.socialmediaplatform.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс-DTO для описания исключений на платформе
 */
@Data
public class AppErrorDto {
    private Integer status;
    private String message;
    private LocalDateTime timestamp;

    public AppErrorDto(Integer status, String message) {
        LocalDateTime dateTime = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.timestamp = dateTime;
    }
}
