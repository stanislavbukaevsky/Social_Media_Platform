package com.github.stanislavbukaevsky.socialmediaplatform.mapper;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.UserDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    private static final Long ID = 1L;
    private static final String FIRST_NAME = "Иван";
    private static final String LAST_NAME = "Иванов";
    private static final String USERNAME = "IvanIvanov";
    private static final String EMAIL = "ivanov@mail.ru";
    private static final String PASSWORD = "123";
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();
    private static final LocalDateTime UPDATED_AT = LocalDateTime.now();
    @InjectMocks
    private UserMapperImpl userMapper;

    @Test
    public void toUserDtoTest() {
        User user = new User();
        user.setId(ID);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setCreatedAt(CREATED_AT);
        user.setUpdatedAt(UPDATED_AT);
        user.setRole(Role.USER);

        UserDto userDto = userMapper.toUserDto(user);

        Assertions.assertThat(userDto).isNotNull();
        Assertions.assertThat(userDto.getId()).isEqualTo(ID);
        Assertions.assertThat(userDto.getFirstName()).isEqualTo(FIRST_NAME);
        Assertions.assertThat(userDto.getLastName()).isEqualTo(LAST_NAME);
        Assertions.assertThat(userDto.getUsername()).isEqualTo(USERNAME);
        Assertions.assertThat(userDto.getEmail()).isEqualTo(EMAIL);
        Assertions.assertThat(userDto.getPassword()).isEqualTo(PASSWORD);
        Assertions.assertThat(userDto.getCreatedAt()).isEqualTo(CREATED_AT);
        Assertions.assertThat(userDto.getUpdatedAt()).isEqualTo(UPDATED_AT);
        Assertions.assertThat(userDto.getRole()).isEqualTo(Role.USER);
    }
}
