package com.vifrin.user.messaging;


import com.vifrin.common.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserEventSender {

    private UserEventStream channels;

    public UserEventSender(UserEventStream channels) {
        this.channels = channels;
    }

    public void sendEventFollow(Long fromId, Long toId) {
        UserEventPayload userEventPayload = UserEventPayload.builder()
                .eventType(UserEventType.FOLLOWED)
                .userId(fromId)
                .toId(toId).build();
        sendUserEvent(userEventPayload);
    }

    private void sendUserEvent(UserEventPayload payload) {

        Message<UserEventPayload> message =
                MessageBuilder
                        .withPayload(payload)
                        .setHeader(KafkaHeaders.MESSAGE_KEY, String.valueOf(payload.getToId()))
                        .build();

        channels.userChanged().send(message);

        log.info("post event {} sent to topic {} for user {} follow user {}",
                message.getPayload().getEventType().name(),
                channels.OUTPUT,
                message.getPayload().getUserId(),
                message.getPayload().getToId());
    }

    private UserEventPayload convertTo(Post post, UserEventType eventType) {
        return UserEventPayload
                .builder()
                .eventType(eventType)
                .userId(post.getId())
                .userId(post.getUser().getId())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
