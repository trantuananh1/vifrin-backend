package com.vifrin.feed.controller;

import com.vifrin.common.config.constant.BaseConstant;
import com.vifrin.common.dto.PostDto;
import com.vifrin.feed.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Thu, 09/12/2021
 **/

@RestController
@RequestMapping("/feed")
@Slf4j
public class FeedController {
    @Autowired
    FeedService feedService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getFeed(@AuthenticationPrincipal Principal principal,
                                                 @RequestParam(defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size) {
        log.info("fetching feed for user {} page {}",
                principal.getName(), page);
        List<PostDto> postDtos = feedService.getUserFeed(principal.getName(), page, size);
        return postDtos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(postDtos);
    }
}
