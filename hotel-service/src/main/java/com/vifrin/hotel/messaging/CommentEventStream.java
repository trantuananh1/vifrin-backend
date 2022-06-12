package com.vifrin.hotel.messaging;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CommentEventStream {

    String INPUT = "CommentChanged";

    @Input(INPUT)
    SubscribableChannel commentChanged();
}
