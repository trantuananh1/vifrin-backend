package com.vifrin.feed.controller;

import com.vifrin.common.payload.post.PostDto;
import com.vifrin.feed.payload.SlicedResult;
import com.vifrin.feed.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author: trantuananh1
 * @since: Thu, 09/12/2021
 **/

@RestController
@RequestMapping("/feed")
@Slf4j
public class FeedController {
    @Autowired
    FeedService feedService;

    @GetMapping
    public ResponseEntity<SlicedResult<PostDto>> getFeed(
            @PathVariable String username,
            @RequestParam(value = "ps",required = false) Optional<String> pagingState) {

        log.info("fetching feed for user {} isFirstPage {}",
                username, pagingState.isEmpty());

        return ResponseEntity.ok(feedService.getUserFeed(username, pagingState));
    }
}
