package com.vifrin.notification.service;

import com.vifrin.common.config.constant.StringPool;
import com.vifrin.common.dto.FollowDto;
import com.vifrin.common.dto.NotificationDto;
import com.vifrin.common.entity.*;
import com.vifrin.common.payload.NotificationData;
import com.vifrin.common.repository.*;
import com.vifrin.common.type.NotificationType;
import com.vifrin.feign.client.UserFeignClient;
import com.vifrin.notification.mapper.NotificationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: trantuananh1
 * @since: Wed, 22/12/2021
 **/

@Service
@Slf4j
public class NotificationService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NotificationChangeRepository notificationChangeRepository;
    @Autowired
    NotificationObjectRepository notificationObjectRepository;
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    UserFeignClient userFeignClient;

    public List<NotificationDto> getNotifications(String username, int page, int size){
        User user = userRepository.findByUsername(username).get();
        Pageable pageable = PageRequest.of(page, size);
        List<Notification> notifications = notificationRepository.getByUserIdOrderByCreatedAtDesc(user.getId(), pageable);
        return notificationMapper.notificationsToNotificationDtos(notifications);
    }

    public void notifyChange(NotificationType notificationType, Long entityId, Long actorId){
        NotificationData notificationData = generateNotificationData(notificationType, entityId, actorId);
        NotificationObject notificationObject = insertNotificationObject(notificationData);
        insertNotificationChange(actorId, notificationObject);
        for (Long notifierId : notificationData.getNotifierIds()){
            User notifier = userRepository.getOne(notifierId);
            notificationRepository.save(new Notification(notificationObject, notifier));
        }
    }

    public NotificationData generateNotificationData(NotificationType notificationType, Long entityId, Long actorId){
        List<Long> notifierIds = getNotifierIds(notificationType, entityId, actorId);
        return new NotificationData(notificationType.getValue(), entityId, actorId, notifierIds);
    }

    public NotificationObject insertNotificationObject(NotificationData notificationData){
        NotificationObject notificationObject = new NotificationObject(notificationData.getNotificationType(), notificationData.getEntityId());
        notificationObject = notificationObjectRepository.save(notificationObject);
        return notificationObject;
    }

    public void insertNotificationChange(Long actorId, NotificationObject notificationObject){
        User user = userRepository.findById(actorId).get();
        NotificationChange notificationChange = new NotificationChange(user, notificationObject);
        notificationChangeRepository.save(notificationChange);
    }

    public String generateContent(Notification notification){
        String username = notification.getUser().getUsername();
        NotificationType notificationType = NotificationType.getByValue(notification.getNotificationObject().getType());
        switch (notificationType){
            case ADD_POST:
            case LIKE_POST:
                Post post = postRepository.findById(notification.getNotificationObject().getEntityId()).get();
                String postContent = post.getContent();
                return String.format(notificationType.getTemplate(), username, postContent);
            case COMMENT_POST:
                Comment comment = commentRepository.findById(notification.getNotificationObject().getEntityId()).get();
                String commentContent = comment.getContent();
                return String.format(notificationType.getTemplate(), username, commentContent);
            case FOLLOW:
                return String.format(notificationType.getTemplate(), username);
        }
        return StringPool.BLANK;
    }

    public List<Long> getNotifierIds(NotificationType notificationType, Long entityId, Long actorId){
        List<Long> notifierIds = new ArrayList<>();
        switch (notificationType){
            case ADD_POST:
                List<FollowDto> followDtos = userFeignClient.getFollowers(actorId, 0, Integer.MAX_VALUE).getBody();
                notifierIds = followDtos.stream().map(FollowDto::getUserId).collect(Collectors.toList());
                break;
            case COMMENT_POST:
                Long postId = commentRepository.getPostIdByCommentId(entityId);
                Post post= postRepository.findById(postId).get();
                notifierIds.add(post.getUser().getId());
                break;
            case FOLLOW:
                notifierIds.add(entityId);
                break;
        }
        return notifierIds;

    }
}
