package com.vifrin.comment.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CommentEventStream {

    String OUTPUT = "CommentChanged";

    @Output(OUTPUT)
    MessageChannel commentChanged();
}
