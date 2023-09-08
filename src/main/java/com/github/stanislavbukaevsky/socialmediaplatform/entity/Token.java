package com.github.stanislavbukaevsky.socialmediaplatform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс-сущность для всех jwt refresh-токенов
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "date_and_time")
    private LocalDateTime dateTime;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
