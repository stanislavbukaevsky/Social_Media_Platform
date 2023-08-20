package com.github.stanislavbukaevsky.socialmediaplatform.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс-сущность для всех пользователей
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Уникальный идентификатор пользователя")
    private Long id;
    @Column(name = "first_name")
    @Schema(description = "Имя пользователя")
    private String firstName;
    @Column(name = "last_name")
    @Schema(description = "Фамилия пользователя")
    private String lastName;
    @Column(name = "username")
    @Schema(description = "Имя пользователя при регистрации и авторизации")
    private String username;
    @Column(name = "email")
    @Schema(description = "Электронная почта пользователя")
    private String email;
    @Column(name = "password")
    @Schema(description = "Пароль пользователя")
    private String password;
    @Column(name = "created_at")
    @Schema(description = "Дата регистрации пользователя пользователя")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @Schema(description = "Дата изменения профиля пользователя")
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Schema(description = "Роль пользователя")
    private Role role;
    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.TRUE)
    @Schema(description = "Список постов, опубликованных пользователем")
    private List<Post> posts;
    @ManyToMany
    @JoinTable(name = "user_subscriptions", joinColumns = @JoinColumn(name = "subscriber_id"), inverseJoinColumns = @JoinColumn(name = "channel_id"))
    @JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "Друзья пользователя")
    private Set<User> friends = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "user_subscriptions", joinColumns = @JoinColumn(name = "channel_id"), inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    @JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "Подписчики пользователя")
    private Set<User> subscribers = new HashSet<>();
}
