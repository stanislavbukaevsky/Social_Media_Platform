package com.github.stanislavbukaevsky.socialmediaplatform.mapper;

import com.github.stanislavbukaevsky.socialmediaplatform.dto.FriendsDto;
import com.github.stanislavbukaevsky.socialmediaplatform.dto.SubscriptionDto;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.Subscription;
import com.github.stanislavbukaevsky.socialmediaplatform.entity.User;
import com.github.stanislavbukaevsky.socialmediaplatform.enums.Status;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class SubscriptionMapperTest {
    private static final Long ID = 1L;
    private static final Long ID_SENDER = 2L;
    private static final String USERNAME_SENDER = "IvanIvanov";
    private static final Long ID_RECIPIENT = 3L;
    private static final String USERNAME_RECIPIENT = "PetrPetrov";
    private static final Integer SENDER_APP = 2;
    private static final Integer RECIPIENT_APP = 3;
    private static final LocalDateTime CREATED_AT = LocalDateTime.now();
    private static final LocalDateTime UPDATED_AT = LocalDateTime.now();
    @InjectMocks
    private SubscriptionMapperImpl subscriptionMapper;

    @Test
    public void toSubscriptionDtoTest() {
        User userSender = new User();
        userSender.setId(ID_SENDER);
        User userRecipient = new User();
        userRecipient.setId(ID_RECIPIENT);
        Subscription subscription = new Subscription();
        subscription.setId(ID);
        subscription.setSenderApplication(userSender);
        subscription.setRecipientApplication(userRecipient);
        subscription.setCreatedAt(CREATED_AT);
        subscription.setUpdatedAt(UPDATED_AT);
        subscription.setStatus(Status.CONSIDERATION);

        SubscriptionDto subscriptionDto = subscriptionMapper.toSubscriptionDto(subscription);

        Assertions.assertThat(subscriptionDto).isNotNull();
        Assertions.assertThat(subscriptionDto.getSenderApp()).isEqualTo(SENDER_APP);
        Assertions.assertThat(subscriptionDto.getRecipientApp()).isEqualTo(RECIPIENT_APP);
        Assertions.assertThat(subscriptionDto.getStatus()).isEqualTo(Status.CONSIDERATION);

    }

    @Test
    public void toFriendsDtoTest() {
        User userSender = new User();
        userSender.setId(ID_SENDER);
        userSender.setUsername(USERNAME_SENDER);
        User userRecipient = new User();
        userRecipient.setId(ID_RECIPIENT);
        userRecipient.setUsername(USERNAME_RECIPIENT);
        Subscription subscription = new Subscription();
        subscription.setId(ID);
        subscription.setSenderApplication(userSender);
        subscription.setRecipientApplication(userRecipient);
        subscription.setCreatedAt(CREATED_AT);
        subscription.setUpdatedAt(UPDATED_AT);
        subscription.setStatus(Status.ACCEPTANCE);

        FriendsDto friendsDto = subscriptionMapper.toFriendsDto(subscription);

        Assertions.assertThat(friendsDto).isNotNull();
        Assertions.assertThat(friendsDto.getSenderApp()).isEqualTo(SENDER_APP);
        Assertions.assertThat(friendsDto.getUsernameSender()).isEqualTo(USERNAME_SENDER);
        Assertions.assertThat(friendsDto.getRecipientApp()).isEqualTo(RECIPIENT_APP);
        Assertions.assertThat(friendsDto.getUsernameRecipient()).isEqualTo(USERNAME_RECIPIENT);
        Assertions.assertThat(friendsDto.getStatus()).isEqualTo(Status.ACCEPTANCE);
    }
}
