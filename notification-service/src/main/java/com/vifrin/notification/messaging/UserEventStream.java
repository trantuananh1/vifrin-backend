package com.vifrin.notification.messaging;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author: trantuananh1
 * @since: Fri, 24/12/2021
 **/

public interface UserEventStream {
    String INPUT = "UserChanged";

    @Input(INPUT)
    SubscribableChannel userChanged();
}
