package com.github.stanislavbukaevsky.socialmediaplatform.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс-сущность для всех изображений
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Уникальный идентификатор изображения")
    private Long id;
    @Column(name = "file_path")
    @Schema(description = "Путь к изображению")
    private String filePath;
    @Column(name = "file_size")
    @Schema(description = "Размер изображения")
    private Long fileSize;
    @Column(name = "media_type")
    @Schema(description = "Расширение изображения")
    private String mediaType;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Schema(description = "Размер изображения в байтах")
    private byte[] data;
    @Column(name = "created_at")
    @Schema(description = "Дата опубликования изображения")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @Schema(description = "Дата изменения изображения")
    private LocalDateTime updatedAt;
    @OneToOne
    @JoinColumn(name = "post_id")
    @Schema(description = "Пост, опубликованный пользователем")
    private Post post;
}
