package com.github.stanislavbukaevsky.socialmediaplatform.mapper;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.JwtResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtMapperTest {
    private static final Long ID = 1L;
    private static final String USERNAME = "IvanIvanov";
    private static final String EMAIL = "ivanov@mail.ru";
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    @InjectMocks
    private JwtMapperImpl jwtMapper;

    @Test
    public void toJwtDtoTest() {
        JwtResponseDto jwtResponseDto = jwtMapper.toJwtDto(ID, USERNAME, EMAIL, TOKEN);

        Assertions.assertThat(jwtResponseDto).isNotNull();
        Assertions.assertThat(jwtResponseDto.getId()).isEqualTo(ID);
        Assertions.assertThat(jwtResponseDto.getUsername()).isEqualTo(USERNAME);
        Assertions.assertThat(jwtResponseDto.getEmail()).isEqualTo(EMAIL);
        Assertions.assertThat(jwtResponseDto.getToken()).isEqualTo(TOKEN);
    }
}
