package com.github.stanislavbukaevsky.socialmediaplatform.utils;

import com.github.stanislavbukaevsky.socialmediaplatform.security.UserSecurityService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Этот класс пропускает запрос через фильтр.
 * Класс наследуется от абстрактного класса {@link GenericFilterBean}
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private static final String AUTHORIZATION = "Authorization";
    private final UserSecurityService userSecurityService;
    private final JwtProvider jwtProvider;

    /**
     * Этот метод фильтрует доступ к ресурсам по jwt токену
     *
     * @param servletRequest  запрос пользователя
     * @param servletResponse ответ пользователю
     * @param filterChain     фильтр сервлета
     * @throws IOException      общий класс исключений ввода-вывода
     * @throws ServletException исключение сервлета
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtProvider.validateAccessToken(token)) {
            final Claims claims = jwtProvider.getAccessClaims(token);
            final UserDetails userDetails = userSecurityService.loadUserByUsername(claims.getSubject());
            UsernamePasswordAuthenticationToken detailsUser = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            detailsUser.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) servletRequest));
            SecurityContextHolder.getContext().setAuthentication(detailsUser);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Приватный метод для передачи токена по запросу пользователя
     *
     * @param request запрос пользователя
     * @return Возвращает токен по запросу пользователя
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
