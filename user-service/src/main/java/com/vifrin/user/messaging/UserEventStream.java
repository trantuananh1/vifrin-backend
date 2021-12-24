package com.vifrin.user.messaging;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface UserEventStream {

    String OUTPUT = "UserChanged";

    @Output(OUTPUT)
    MessageChannel userChanged();
}
