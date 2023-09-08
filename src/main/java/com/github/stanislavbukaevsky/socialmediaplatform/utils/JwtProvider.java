package com.github.stanislavbukaevsky.socialmediaplatform.utils;

import com.github.stanislavbukaevsky.socialmediaplatform.security.UserSecurity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Класс для генерации и валидации access и refresh токенов
 */
@Component
@Slf4j
public class JwtProvider {
    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    public JwtProvider(@Value("${jwt.secret.access}") String jwtAccessSecret,
                       @Value("${jwt.secret.refresh}") String jwtRefreshSecret) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    /**
     * Этот метод генерирует access токен
     *
     * @param userSecurity пользовательские данные
     * @return Возвращает сгенерированный JWT access токен
     */
    public String generateAccessToken(@NonNull UserSecurity userSecurity) {
        final LocalDateTime createdDate = LocalDateTime.now();
        final Instant accessExpirationInstant = createdDate.plusMinutes(10).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(userSecurity.getUsername())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("roles", userSecurity.getUser().getRole())
                .compact();
    }

    /**
     * Этот метод генерирует refresh токен
     *
     * @param userSecurity пользовательские данные
     * @return Возвращает сгенерированный JWT refresh токен
     */
    public String generateRefreshToken(@NonNull UserSecurity userSecurity) {
        final LocalDateTime createdDate = LocalDateTime.now();
        final Instant refreshExpirationInstant = createdDate.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);

        return Jwts.builder()
                .setSubject(userSecurity.getUsername())
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    /**
     * Этот метод отвечает за проверку валидности access токена
     *
     * @param accessToken access токен
     * @return Возвращает true или false, в зависимости от того валиден access токен или нет
     */
    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    /**
     * Этот метод отвечает за проверку валидности refresh токена
     *
     * @param refreshToken refresh токен
     * @return Возвращает true или false, в зависимости от того валиден refresh токен или нет
     */
    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    /**
     * Этот метод формирует из access токена данные о пользователе
     *
     * @param token access токен
     * @return Возвращает сгенерированные данные о пользователе по его access токену
     */
    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    /**
     * Этот метод формирует из refresh токена данные о пользователе
     *
     * @param token refresh токен
     * @return Возвращает сгенерированные данные о пользователе по его refresh токену
     */
    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    /**
     * Приватный метод для генерации валидации токенов
     *
     * @param token  токен
     * @param secret секретный ключ
     * @return Возвращает true или false, в зависимости от того валиден токен или нет
     */
    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Срок действия токена истек!", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Неподдерживаемый токен!", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Неправильно сформированный токен!", mjEx);
        } catch (SignatureException sEx) {
            log.error("Недействительная подпись!", sEx);
        } catch (Exception e) {
            log.error("Недопустимый токен!", e);
        }
        return false;
    }

    /**
     * Приватный метод для генерации пользовательских данных по токену
     *
     * @param token  токен
     * @param secret секретный ключ
     * @return Возвращает сгенерированные данные о пользователе по его токену
     */
    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
