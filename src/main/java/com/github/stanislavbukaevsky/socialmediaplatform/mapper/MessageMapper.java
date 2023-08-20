package com.github.stanislavbukaevsky.socialmediaplatform.mapper;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.MessageDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Маппер-интерфейс, который преобразует сущность всех сообщений, опубликованных на платформе {@link Message}
 * в DTO {@link MessageDto}
 */
@Mapper
public interface MessageMapper {

    /**
     * Этот метод конвертирует сущность сообщений в DTO сообщений. <br>
     * Используется аннотация {@link Mapping} для соответствия полей
     *
     * @param message сущность сообщений
     * @return Возвращает сконвертированную DTO сообщений, отправленных на платформе
     */
    @Mapping(source = "message.senderMessage.id", target = "senderMsg")
    @Mapping(source = "message.recipientMessage.id", target = "recipientMsg")
    @Mapping(source = "text", target = "text")
    @Mapping(source = "createdAt", target = "dateTime")
    MessageDto toMessageDto(Message message);
}
