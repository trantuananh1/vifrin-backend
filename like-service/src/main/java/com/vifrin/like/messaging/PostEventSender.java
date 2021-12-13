package com.vifrin.like.messaging;


import com.vifrin.common.dto.PostDto;
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

    public void sendPostCreated(PostDto postDto) {
        log.info("sending post created event for post id {}", postDto.getId());
        sendPostChangedEvent(convertTo(postDto, PostEventType.CREATED));
    }

    public void sendPostUpdated(PostDto postDto) {
        log.info("sending post updated event for post {}", postDto.getId());
        sendPostChangedEvent(convertTo(postDto, PostEventType.UPDATED));
    }

    public void sendPostDeleted(PostDto postDto) {
        log.info("sending post deleted event for post {}", postDto.getId());
        sendPostChangedEvent(convertTo(postDto, PostEventType.DELETED));
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

    private PostEventPayload convertTo(PostDto postDto, PostEventType eventType) {
        return PostEventPayload
                .builder()
                .eventType(eventType)
                .postId(postDto.getId())
                .userId(postDto.getUserId())
                .createdAt(postDto.getCreatedAt())
                .build();
    }
}
