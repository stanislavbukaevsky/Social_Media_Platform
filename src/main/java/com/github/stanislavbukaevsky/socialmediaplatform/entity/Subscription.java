package com.github.stanislavbukaevsky.socialmediaplatform.entity;

import com.github.stanislavbukaevsky.socialmediaplatform.enums.Status;
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
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_app_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User senderApplication;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_app_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User recipientApplication;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
