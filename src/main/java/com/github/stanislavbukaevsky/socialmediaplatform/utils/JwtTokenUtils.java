package com.github.stanislavbukaevsky.socialmediaplatform.utils;
//
//import com.github.stanislavbukaevsky.socialmediaplatform.security.UserSecurity;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * Класс для работы с токенами
// */
//@Component
public class JwtTokenUtils {
//    @Value("${jwt.secret}")
//    private String secret;
//    @Value("${jwt.lifetime}")
//    private Duration jwtLifeTime;
//
//    /**
//     * Этот метод формирует из пользовательских данных JWT-токен
//     *
//     * @param userSecurity пользовательские данные
//     * @return Возвращает сгенерированный JWT-токен
//     */
//    public String generateToken(UserSecurity userSecurity) {
//        Map<String, Object> claims = new HashMap<>();
//        List<String> rolesListUsers = userSecurity.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toList());
//        claims.put("roles", rolesListUsers);
//
//        Date issuedDate = new Date();
//        Date expiredDate = new Date(issuedDate.getTime() + jwtLifeTime.toMillis());
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(userSecurity.getUsername())
//                .setIssuedAt(issuedDate)
//                .setExpiration(expiredDate)
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact();
//    }
//
//    /**
//     * Этот метод позволяет из токена получить имя пользователя
//     *
//     * @param token токен
//     * @return Возвращает имя пользователя взятое из токена
//     */
//    public String getUsername(String token) {
//        return getAllClaimsFromToken(token).getSubject();
//    }
//
//    /**
//     * Этот метод позволяет из токена получить список ролей пользователя
//     *
//     * @param token токен
//     * @return Возвращает список ролей пользователя взятые из токена
//     */
//    public List<String> getRoles(String token) {
//        return getAllClaimsFromToken(token).get("roles", List.class);
//    }
//
//    /**
//     * Приватный метод, который формирует из токена данные о пользователе
//     *
//     * @param token токен
//     * @return Возвращает сформированные пользовательские данные
//     */
//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody();
//    }
}
