package com.vifrin.feed.service;

import com.vifrin.common.constant.BaseConstant;
import com.vifrin.common.dto.FollowDto;
import com.vifrin.common.payload.PagedResult;
import com.vifrin.common.payload.post.PostDto;
import com.vifrin.feed.entity.UserFeed;
import com.vifrin.feed.exception.UnableToGetFollowersException;
import com.vifrin.feed.repository.FeedRepository;
import com.vifrin.feign.client.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class FeedGeneratorService {
    @Autowired
    UserFeignClient userFeignClient;
    @Autowired
    FeedRepository feedRepository;

    public void addToFeed(PostDto postDto) {
        log.info("adding post {} to feed for user {}" ,
                postDto.getId(), postDto.getUserId());

        boolean isLast = false;
        int page = 0;
        int size = BaseConstant.PAGE_SIZE;

        while(!isLast) {
            ResponseEntity<PagedResult<FollowDto>> response = userFeignClient.getFollowers(postDto.getUserId(), page, size);
            if(response.getStatusCode().is2xxSuccessful()) {

                PagedResult<FollowDto> followDtos = response.getBody();

                log.info("found {} followers for user {}, page {}",
                        followDtos.getTotalElements(), postDto.getUserId(), page);

                followDtos.getContent()
                        .stream()
                        .map(followDto -> convertTo(followDto, postDto))
                        .forEach(feedRepository::insert);

                isLast = followDtos.isLast();
                page++;

            } else {
                String message =
                        String.format("unable to get followers for user %s", postDto.getUserId());

                log.warn(message);
                throw new UnableToGetFollowersException(message);
            }
        }
    }

    private UserFeed convertTo(FollowDto followDto, PostDto postDto) {
        return UserFeed
                .builder()
                .userId(followDto.getUserId())
                .username(followDto.getUsername())
                .postId(postDto.getId())
                .createdAt(postDto.getCreatedAt())
                .build();
    }
}
