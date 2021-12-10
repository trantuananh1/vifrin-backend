package com.vifrin.feed.payload;

import com.vifrin.feed.messaging.PostEventType;
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
