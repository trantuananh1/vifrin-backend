package com.vifrin.notification.service.object;

import com.vifrin.common.dto.FollowDto;
import com.vifrin.common.entity.Post;
import com.vifrin.common.entity.User;
import com.vifrin.common.repository.PostRepository;
import com.vifrin.common.type.NotificationType;
import com.vifrin.feign.client.UserFeignClient;
import com.vifrin.notification.service.NotificationExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: trantuananh1
 * @since: Thu, 23/12/2021
 **/

@Service
public class PostNotificationExecutor implements NotificationExecutor {
    @Autowired
    UserFeignClient userFeignClient;

    @Override
    public List<Long> getNotifierIds(NotificationType notificationType, Long entityId, Long actorId) {
        //get followers
        List<FollowDto> followDtos = userFeignClient.getFollowers(actorId, 0, Integer.MAX_VALUE).getBody();
        return followDtos.stream().map(FollowDto::getUserId).collect(Collectors.toList());
    }
}
