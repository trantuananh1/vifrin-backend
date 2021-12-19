package com.vifrin.comment.messaging;


import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.dto.PostDto;
import com.vifrin.common.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CommentEventSender {

    private CommentEventStream channels;

    public CommentEventSender(CommentEventStream channels) {
        this.channels = channels;
    }

    public void sendCommentCreated(Comment comment) {
        log.info("sending comment created event for comment id {}", comment.getId());
        sendCommentChangedEvent(convertTo(comment, CommentEventType.CREATED));
    }

    private void sendCommentChangedEvent(CommentEventPayload payload) {

        Message<CommentEventPayload> message =
                MessageBuilder
                        .withPayload(payload)
                        .setHeader(KafkaHeaders.MESSAGE_KEY, String.valueOf(payload.getTargetId()))
                        .build();

        channels.commentChanged().send(message);

        log.info("post event {} sent to topic {} for post {} and user {}",
                message.getPayload().getEventType().name(),
                channels.OUTPUT,
                message.getPayload().getTargetId(),
                message.getPayload().getUserId());
    }

    private CommentEventPayload convertTo(Comment comment, CommentEventType eventType) {
        return CommentEventPayload
                .builder()
                .eventType(eventType)
                .targetId(comment.getDestination().getId())
                .score(comment.getScore())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
