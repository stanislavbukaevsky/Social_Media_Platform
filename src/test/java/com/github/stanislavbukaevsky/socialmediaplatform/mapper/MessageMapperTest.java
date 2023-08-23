package com.github.stanislavbukaevsky.socialmediaplatform.mapper;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.MessageDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Message;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class MessageMapperTest {
    private static final Long ID = 1L;
    private static final Long ID_SENDER = 2L;
    private static final Long ID_RECIPIENT = 3L;
    private static final String TEXT = "Текстовое сообщение";
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();
    private static final String DESCRIPTION = "Описание";
    @InjectMocks
    private MessageMapperImpl messageMapper;

    @Test
    public void toMessageDtoTest() {
        User userSender = new User();
        userSender.setId(ID_SENDER);
        User userRecipient = new User();
        userRecipient.setId(ID_RECIPIENT);
        Message message = new Message();
        message.setId(ID);
        message.setText(TEXT);
        message.setSenderMessage(userSender);
        message.setRecipientMessage(userRecipient);
        message.setCreatedAt(CREATED_AT);

        MessageDto messageDto = messageMapper.toMessageDto(message);
        messageDto.setDescription(DESCRIPTION);

        Assertions.assertThat(messageDto).isNotNull();
        Assertions.assertThat(messageDto.getText()).isEqualTo(TEXT);
        Assertions.assertThat(messageDto.getSenderMsg()).isEqualTo(ID_SENDER.intValue());
        Assertions.assertThat(messageDto.getRecipientMsg()).isEqualTo(ID_RECIPIENT.intValue());
        Assertions.assertThat(messageDto.getDateTime()).isEqualTo(CREATED_AT);
        Assertions.assertThat(messageDto.getDescription()).isEqualTo(DESCRIPTION);
    }
}
