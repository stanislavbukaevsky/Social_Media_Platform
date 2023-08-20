package com.github.stanislavbukaevsky.socialmediaplatform.entity;

import com.github.stanislavbukaevsky.socialmediaplatform.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс-сущность для всех подписок и заявок в друзья
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Уникальный идентификатор подписки")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_app_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "Пользователь, который отправляет заявку в друзья")
    private User senderApplication;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reciptient_app_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "Пользователь, который получает заявку в друзья")
    private User recipientApplication;
    @Column(name = "created_at")
    @Schema(description = "Дата отправления пользователем заявки в друзья")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @Schema(description = "Дата изменения пользователем заявки в друзья")
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Schema(description = "Статус отправленной заявки в друзья")
    private Status status;
}
