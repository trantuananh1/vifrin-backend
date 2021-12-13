package com.vifrin.like.controller;

import com.vifrin.common.dto.LikeDto;
import com.vifrin.common.entity.Post;
import com.vifrin.common.repository.LikeRepository;
import com.vifrin.common.repository.PostRepository;
import com.vifrin.common.repository.UserRepository;
import com.vifrin.like.service.LikeService;
import com.vifrin.post.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author: trantuananh1
 * @since: Mon, 13/12/2021
 **/

@RestController
@RequestMapping("/likes")
@Slf4j
public class LikeController {
    @Autowired
    LikeService likeService;

    @PostMapping
    public ResponseEntity<?> addLike(@RequestBody LikeDto likeDto, @AuthenticationPrincipal Principal principal){

    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<?> addLike(@PathVariable Long postId, @AuthenticationPrincipal Principal principal){

    }
}
