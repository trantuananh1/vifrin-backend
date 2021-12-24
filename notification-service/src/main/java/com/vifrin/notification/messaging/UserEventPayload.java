package com.vifrin.notification.messaging;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UserEventPayload {
    private Instant createdAt;
    private Long userId;
    private Long toId;
    private UserEventType eventType;
}
