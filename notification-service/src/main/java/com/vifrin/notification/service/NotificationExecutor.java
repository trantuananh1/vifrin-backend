package com.vifrin.notification.service;

import com.vifrin.common.type.NotificationType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Thu, 23/12/2021
 **/

public interface NotificationExecutor {
    public List<Long> getNotifierIds(NotificationType notificationType, Long entityId, Long actorId);
}
