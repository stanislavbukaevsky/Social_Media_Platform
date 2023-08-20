package com.github.stanislavbukaevsky.socialmediaplatform.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс-сущность для всех постов, опубликованных пользователями
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Уникальный идентификатор поста")
    private Long id;
    @Column(name = "title")
    @Schema(description = "Заголовок поста")
    private String title;
    @Column(name = "text")
    @Schema(description = "Текст поста")
    private String text;
    @Column(name = "created_at")
    @Schema(description = "Дата опубликования поста")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @Schema(description = "Дата изменения поста")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "Зарегистрированный пользователь")
    private User user;
    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Schema(description = "Изображение поста")
    private Image image;
}
