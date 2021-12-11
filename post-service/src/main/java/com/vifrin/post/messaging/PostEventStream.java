package com.vifrin.post.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface PostEventStream {

    String OUTPUT = "PostChanged";

    @Output(OUTPUT)
    MessageChannel postChanged();
}
