package com.vifrin.comment.messaging;

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
    private int star;
    private CommentEventType eventType;
}
