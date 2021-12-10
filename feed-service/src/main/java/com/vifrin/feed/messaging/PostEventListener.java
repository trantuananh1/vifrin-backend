package com.vifrin.feed.messaging;

import com.vifrin.common.payload.post.PostDto;
import com.vifrin.feed.payload.PostEventPayload;
import com.vifrin.feed.service.FeedGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostEventListener {

    private FeedGeneratorService feedGeneratorService;

    public PostEventListener(FeedGeneratorService feedService) {
        this.feedGeneratorService = feedService;
    }

    @StreamListener(PostEventStream.INPUT)
    public void onMessage(Message<PostEventPayload> message) {

        PostEventType eventType = message.getPayload().getEventType();

        log.info("received message to process post {} for user {} eventType {}",
                message.getPayload().getPostId(),
                message.getPayload().getUserId(),
                eventType.name());

        Acknowledgment acknowledgment =
                message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);


        switch (eventType) {
            case CREATED:
                feedGeneratorService.addToFeed(convertTo(message.getPayload()));
                break;
            case DELETED:
                break;
        }

        if(acknowledgment != null) {
            acknowledgment.acknowledge();
        }
    }

    private PostDto convertTo(PostEventPayload payload) {
        return PostDto
                .builder()
                .id(payload.getPostId())
                .createdAt(payload.getCreatedAt())
                .userId(payload.getUserId())
                .build();
    }
}
