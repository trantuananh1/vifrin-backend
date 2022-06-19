package com.vifrin.notification.mapper;

import com.vifrin.common.dto.NotificationDto;
import com.vifrin.common.dto.UserSummary;
import com.vifrin.common.entity.Notification;
import com.vifrin.common.entity.User;
import com.vifrin.common.repository.PostRepository;
import com.vifrin.common.type.NotificationType;
import com.vifrin.common.util.RedisUtil;
import com.vifrin.feign.client.UserFeignClient;
import com.vifrin.notification.service.AuthService;
import com.vifrin.notification.service.NotificationService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Thu, 23/12/2021
 **/

@Mapper(componentModel = "spring")
public abstract class NotificationMapper {
    @Autowired
    UserFeignClient userFeignClient;
    @Autowired
    NotificationService notificationService;
    @Autowired
    AuthService authService;

    @Mapping(target = "user", expression = "java(getUserSummary(notification))")
    @Mapping(target = "content", expression = "java(getContent(notification))")
    @Mapping(target = "createdAt", source = "notification.createdAt")
    public abstract NotificationDto notificationToNotificationDto(Notification notification);

    public abstract List<NotificationDto> notificationsToNotificationDtos(List<Notification> notifications);

    UserSummary getUserSummary(Notification notification){
        return userFeignClient.getUserSummary(notification.getUser().getId(), authService.getAccessToken()).getBody();
    }

    String getContent(Notification notification){
        return notificationService.generateContent(notification);
    }
}
