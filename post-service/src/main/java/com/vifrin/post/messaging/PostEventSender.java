package com.vifrin.post.messaging;


import com.vifrin.common.dto.PostDto;
import com.vifrin.common.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PostEventSender {

    private PostEventStream channels;

    public PostEventSender(PostEventStream channels) {
        this.channels = channels;
    }

    public void sendPostCreated(Post post) {
        log.info("sending post created event for post id {}", post.getId());
        sendPostChangedEvent(convertTo(post, PostEventType.CREATED));
    }

    public void sendPostUpdated(Post post) {
        log.info("sending post updated event for post {}", post.getId());
        sendPostChangedEvent(convertTo(post, PostEventType.UPDATED));
    }

    public void sendPostDeleted(Post post) {
        log.info("sending post deleted event for post {}", post.getId());
        sendPostChangedEvent(convertTo(post, PostEventType.DELETED));
    }

    private void sendPostChangedEvent(PostEventPayload payload) {

        Message<PostEventPayload> message =
                MessageBuilder
                        .withPayload(payload)
                        .setHeader(KafkaHeaders.MESSAGE_KEY, String.valueOf(payload.getPostId()))
                        .build();

        channels.postChanged().send(message);

        log.info("post event {} sent to topic {} for post {} and user {}",
                message.getPayload().getEventType().name(),
                channels.OUTPUT,
                message.getPayload().getPostId(),
                message.getPayload().getUserId());
    }

    private PostEventPayload convertTo(Post post, PostEventType eventType) {
        return PostEventPayload
                .builder()
                .eventType(eventType)
                .postId(post.getId())
                .userId(post.getUser().getId())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
