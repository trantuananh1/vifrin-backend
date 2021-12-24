package com.vifrin.notification.service;

import com.vifrin.common.type.NotificationType;
import com.vifrin.notification.service.object.PostNotificationExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Thu, 23/12/2021
 **/

@Service
public class NotificationTargetProxy implements NotificationExecutor{
    NotificationExecutor notificationExecutor = null;

    @Override
    public List<Long> getNotifierIds(NotificationType notificationType, Long entityId, Long actorId){
        switch (notificationType.getEntityType()){
            case POST:
                notificationExecutor = new PostNotificationExecutor();
                break;
            case COMMENT:
                break;
        }
        if (notificationExecutor == null){
            return null;
        }
        return notificationExecutor.getNotifierIds(notificationType, entityId, actorId);
    }

}
