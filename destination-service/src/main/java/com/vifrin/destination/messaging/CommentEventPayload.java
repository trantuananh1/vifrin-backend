package com.vifrin.destination.messaging;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CommentEventPayload {

    private Long targetId;
    private Instant createdAt;
    private Instant updatedAt;
    private Long userId;
    private int score;
    private CommentEventType eventType;

}
