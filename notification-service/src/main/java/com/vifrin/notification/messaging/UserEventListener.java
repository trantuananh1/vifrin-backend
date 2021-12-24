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

/**
 * @author: trantuananh1
 * @since: Fri, 24/12/2021
 **/

@Component
@Slf4j
public class UserEventListener {
    @Autowired
    NotificationService notificationService;

    @StreamListener(UserEventStream.INPUT)
    public void onMessage(Message<UserEventPayload> message) {
        UserEventType eventType = message.getPayload().getEventType();
        Acknowledgment acknowledgment =
                message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        UserEventPayload payload = message.getPayload();
        switch (eventType) {
            case FOLLOWED:
                notificationService.notifyChange(NotificationType.FOLLOW, payload.getToId(), payload.getUserId());
                break;
        }
        if(acknowledgment != null) {
            acknowledgment.acknowledge();
        }
    }
}
