package com.github.stanislavbukaevsky.socialmediaplatform.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс-сущность для всех сообщений, отправленных пользователями
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Уникальный идентификатор сообщения")
    private Long id;
    @Column(name = "text")
    @Schema(description = "Текстовое сообщение отправленное пользователю")
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_msg_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "Пользователь, который отправляет сообщение другому пользователю")
    private User senderMessage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reciptient_msg_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "Пользователь, который получает сообщение от другого пользователя")
    private User recipientMessage;
    @Column(name = "created_at")
    @Schema(description = "Дата отправления сообщения другому пользователю")
    private LocalDateTime createdAt;
}
