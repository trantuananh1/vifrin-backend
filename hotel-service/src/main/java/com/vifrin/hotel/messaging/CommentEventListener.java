package com.vifrin.hotel.messaging;

import com.vifrin.hotel.service.HotelService;
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
    private HotelService hotelService;

    @StreamListener(CommentEventStream.INPUT)
    public void onMessage(Message<CommentEventPayload> message) {

        CommentEventType eventType = message.getPayload().getEventType();

        log.info("received message to process target {} for user {} eventType {}",
                message.getPayload().getTargetId(),
                message.getPayload().getUserId(),
                eventType.name());

        Acknowledgment acknowledgment =
                message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);


        switch (eventType) {
            case CREATED:
//                hotelService.updateAverageScore(message.getPayload());
                break;
            case DELETED:
                break;
        }

        if(acknowledgment != null) {
            acknowledgment.acknowledge();
        }
    }
}
