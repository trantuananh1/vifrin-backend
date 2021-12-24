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
public class CommentEventListener {

    @Autowired
    private NotificationService notificationService;

    @StreamListener(CommentEventStream.INPUT)
    public void onMessage(Message<CommentEventPayload> message) {

        CommentEventType eventType = message.getPayload().getEventType();
        if (message.getPayload().getUserId() == null){
            return;
        }
        log.info("received message to process target {} for user {} eventType {}",
                message.getPayload().getTargetId(),
                message.getPayload().getUserId(),
                eventType.name());

        Acknowledgment acknowledgment =
                message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);

        CommentEventPayload payload = message.getPayload();
        switch (eventType) {
            case CREATED:
                notificationService.notifyChange(NotificationType.COMMENT_POST, payload.getCommentId(), payload.getUserId());
                break;
            case DELETED:
                break;
        }

        if(acknowledgment != null) {
            acknowledgment.acknowledge();
        }
    }
}
