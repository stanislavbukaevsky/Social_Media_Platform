package com.github.stanislavbukaevsky.socialmediaplatform.entity;

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
    private Long id;
    @Column(name = "text")
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_msg_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User senderMessage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_msg_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User recipientMessage;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
