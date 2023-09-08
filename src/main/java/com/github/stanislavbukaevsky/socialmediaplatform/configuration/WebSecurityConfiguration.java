package com.github.stanislavbukaevsky.socialmediaplatform.configuration;

import com.github.stanislavbukaevsky.socialmediaplatform.utils.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурационный класс для настройки Spring Security
 */
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {
    private final JwtFilter jwtFilter;
    private static final String[] FREE_ACCESS = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/webjars/**",
            "/inputs/registration",
            "/inputs/authentication",
            "/inputs/access-token"
    };
    private static final String[] MODERATOR_AND_ADMIN_ACCESS = {
            "/administrations/message/**"
    };
    private static final String[] ADMIN_ACCESS = {
            "/administrations/update-role/**",
            "/administrations/update-blocking/**"
    };

    /**
     * Этот метод настраивает правила безопасности для работы с приложением и запрещает/разрешает доступ к определенным ресурсам
     *
     * @param http конфигурация http
     * @return Возвращает сконфигурированный и настроенный клиент http
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(auth ->
                        auth
                                .antMatchers(FREE_ACCESS).permitAll()
                                .antMatchers(MODERATOR_AND_ADMIN_ACCESS).hasAnyAuthority("MODERATOR", "ADMIN")
                                .antMatchers(ADMIN_ACCESS).hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                                .and()
                                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class));

        return http.build();
    }

    /**
     * Этот метод используется для шифрования паролей
     *
     * @return Возвращает зашифрованный пароль с защитой 12
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
