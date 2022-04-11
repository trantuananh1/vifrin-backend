package com.vifrin.notification.messaging;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class PostEventPayload {

    private Long postId;
    private Instant createdAt;
    private Instant updatedAt;
    private Long userId;
    private PostEventType eventType;

}
