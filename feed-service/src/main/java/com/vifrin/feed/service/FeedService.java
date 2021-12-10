package com.vifrin.feed.service;

import com.vifrin.common.constant.BaseConstant;
import com.vifrin.common.entity.Post;
import com.vifrin.common.payload.post.PostDto;
import com.vifrin.common.repository.PostRepository;
import com.vifrin.feed.entity.UserFeed;
import com.vifrin.feed.exception.ResourceNotFoundException;
import com.vifrin.feed.mapper.PostMapper;
import com.vifrin.feed.payload.SlicedResult;
import com.vifrin.feed.repository.FeedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * @author: trantuananh1
 * @since: Thu, 09/12/2021
 **/

@Service
@Slf4j
public class FeedService {
    @Autowired
    FeedRepository feedRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostMapper postMapper;

    public SlicedResult<PostDto> getUserFeed(String username, Optional<String> pagingState) {

        log.info("getting feed for user {} page {}", username, pagingState);

        CassandraPageRequest request = pagingState
                .map(pState -> CassandraPageRequest
                        .of(Integer.parseInt(pState), BaseConstant.PAGE_SIZE))
                .orElse(CassandraPageRequest.first(BaseConstant.PAGE_SIZE));

        Slice<UserFeed> page= feedRepository.findByUsername(username, request);

        if(page.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format("Feed not found for user %s", username));
        }

        String pageState = null;

        if(!page.isLast()) {
            pageState = ((CassandraPageRequest)page.getPageable())
                    .getPagingState().toString();
        }

        List<PostDto> posts = getPosts(page);

        return SlicedResult
                .<PostDto>builder()
                .content(posts)
                .isLast(page.isLast())
                .pagingState(pageState)
                .build();
    }

    private List<PostDto> getPosts(Slice<UserFeed> page) {
        List<Long> postIds = page.stream()
                .map(feed -> feed.getPostId())
                .collect(toList());

        List<Post> posts = postRepository.findAllById(postIds);
        return postMapper.postsToPostDtos(posts);
    }
}
