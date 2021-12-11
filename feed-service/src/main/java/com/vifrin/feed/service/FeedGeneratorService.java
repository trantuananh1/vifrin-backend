package com.vifrin.feed.service;

import com.vifrin.common.constant.BaseConstant;
import com.vifrin.common.dto.FollowDto;
import com.vifrin.common.entity.Feed;
import com.vifrin.common.entity.User;
import com.vifrin.common.repository.FeedRepository;
import com.vifrin.common.repository.PostRepository;
import com.vifrin.common.repository.UserRepository;
import com.vifrin.feed.exception.ResourceNotFoundException;
import com.vifrin.feed.exception.UnableToGetFollowersException;
import com.vifrin.feed.messaging.PostEventPayload;
import com.vifrin.feign.client.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
@Slf4j
public class FeedGeneratorService {
    @Autowired
    UserFeignClient userFeignClient;
    @Autowired
    FeedRepository feedRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;

    public void addToFeed(PostEventPayload postEventPayload) {
        long userId = postEventPayload.getUserId();
        long postId = postEventPayload.getPostId();
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException(userId));
        String accessToken = authService.getAccessToken(user.getUsername());

        log.info("adding post {} to feed for user {}" ,
                postId, userId);

        boolean isLast = false;
        int page = 0;
        int size = BaseConstant.PAGE_SIZE;

        while(true) {
            ResponseEntity<List<FollowDto>> response = userFeignClient.getFollowers(userId, page, size, accessToken);
            if(response.getStatusCode().is2xxSuccessful()) {

                List<FollowDto> followDtos = response.getBody();
                if (followDtos == null || followDtos.isEmpty()){
                    break;
                }
                log.info("found {} followers for user {}, page {}",
                        followDtos.size(), userId, page);

                for (FollowDto followDto : followDtos){
                    if (feedRepository.getFeedCountByUsernameAndPostId(followDto.getUsername(), postId) > 0){
                        continue;
                    }
                    Feed feed = new Feed(followDto.getUserId(),followDto.getUsername(), postId, Instant.now());
                    feedRepository.save(feed);
                }
                page++;

            } else {
                String message = String.format("unable to get followers for user %s", userId);
                log.warn(message);
                throw new UnableToGetFollowersException(message);
            }
        }
    }
}
