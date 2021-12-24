package com.vifrin.notification.messaging;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface PostEventStream {

    String INPUT = "PostChanged";

    @Input(INPUT)
    SubscribableChannel postChanged();
}
