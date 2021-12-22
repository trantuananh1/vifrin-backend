package com.vifrin.notification.messaging;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CommentEventPayload {
    private Long targetId;
    private Long commentId;
    private Instant createdAt;
    private Long userId;
    private int score;
    private CommentEventType eventType;
}
