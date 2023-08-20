package com.github.stanislavbukaevsky.socialmediaplatform.configuration;

import com.github.stanislavbukaevsky.socialmediaplatform.security.UserSecurityService;
import com.github.stanislavbukaevsky.socialmediaplatform.utils.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурационный класс для настройки Spring Security
 */
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration {
    private final UserSecurityService userSecurityService;
    private final JwtRequestFilter jwtRequestFilter;
    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/webjars/**",
            "/registration",
            "/authorization"
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
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/post").authenticated()
                .antMatchers("/messages").authenticated()
                .antMatchers("/subscriptions").authenticated()
                .antMatchers("/users").authenticated()
                .mvcMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Этот метод, который используется для аутентификации пользователей, хранящихся в базе данных
     *
     * @return Возвращает аутентифицированного пользователя
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userSecurityService);
        return daoAuthenticationProvider;
    }

    /**
     * Этот метод используется для аутентификации пользователей
     *
     * @param authenticationConfiguration конфигурация аутентификации
     * @return Возвращает аутентифицированного пользователя
     * @throws Exception исключение, если пользователь не аутентифицирован
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
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
