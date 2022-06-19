package com.vifrin.feed.service;

import com.vifrin.common.entity.Feed;
import com.vifrin.common.entity.Post;
import com.vifrin.common.dto.PostDto;
import com.vifrin.common.repository.FeedRepository;
import com.vifrin.common.repository.PostRepository;
import com.vifrin.feed.mapper.PostMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tranmanhhung
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

    public List<PostDto> getUserFeed(String username, int page, int size) {
        List<Post> posts = new ArrayList<>();

        log.info("getting feed for user {} page {}", username, page);
        Pageable pageable = PageRequest.of(page, size);
        List<Long> postIds = feedRepository.getFeedByUsername(username, pageable);

        if (page == 0 && postIds.isEmpty()) {
            posts = postRepository.getPostOrderByCommentsCountDesc(size);
        } else {
            for (Long postId : postIds){
                posts.add(postRepository.getOne(postId));
            }
        }
        return postMapper.postsToPostDtos(posts);
    }
}
