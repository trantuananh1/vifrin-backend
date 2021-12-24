package com.vifrin.notification.messaging;

import com.vifrin.common.type.NotificationType;
import com.vifrin.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostEventListener {
    @Autowired
    private NotificationService notificationService;

    @StreamListener(PostEventStream.INPUT)
    public void onMessage(Message<PostEventPayload> message) {
        PostEventType eventType = message.getPayload().getEventType();
        log.info("received message to process post {} for user {} eventType {}",
                message.getPayload().getPostId(),
                message.getPayload().getUserId(),
                eventType.name());
        Acknowledgment acknowledgment =
                message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        PostEventPayload payload = message.getPayload();
        switch (eventType) {
            case CREATED:
                notificationService.notifyChange(NotificationType.ADD_POST, payload.getPostId(), payload.getUserId());
                break;
        }
        if(acknowledgment != null) {
            acknowledgment.acknowledge();
        }
    }
}
