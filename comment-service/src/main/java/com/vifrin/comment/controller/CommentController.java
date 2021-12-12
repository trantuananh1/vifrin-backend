package com.vifrin.comment.controller;

import com.vifrin.common.constant.BaseConstant;
import com.vifrin.common.constant.StringPool;
import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.dto.PostDto;
import com.vifrin.common.payload.PostRequest;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import com.vifrin.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

/**
 * @author: trantuananh1
 * @since: Sun, 12/12/2021
 **/

@RestController
@RequestMapping("/comments")
@Slf4j
public class CommentController {
    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal Principal principal){
        PostDto postDto = postService.createPost(postRequest, principal.getName());
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/posts/{id}")
                .buildAndExpand(postDto.getId()).toUri();
        return ResponseEntity
                .created(uri)
                .body(new ResponseTemplate<PostDto>(ResponseType.SUCCESS, postDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto, @PathVariable Long id, @AuthenticationPrincipal Principal principal){
        log.info("received a request to update a post with id {}", id);
        postService.updatePost(postRequest, id, principal.getName());
        return ResponseEntity
                .ok(new ResponseTemplate<>(ResponseType.SUCCESS, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id, @AuthenticationPrincipal Principal user){
        log.info("received a request to get a post with id {}", id);
        PostDto postDto = postService.getPost(id);
        return ResponseEntity
                .ok(new ResponseTemplate<PostDto>(ResponseType.SUCCESS, postDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @AuthenticationPrincipal Principal user){
        if (user == null){
            return ResponseEntity.badRequest().body(StringPool.BLANK);
        }
        log.info("received a delete request for post id {} from user {}", id, user.getName());
        postService.deletePost(id, user.getName());
        return ResponseEntity
                .ok(new ResponseTemplate<>(ResponseType.OK, null));
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<?> getCommentsByPost(@PathVariable Long postId, @AuthenticationPrincipal Principal principal,
                                               @RequestParam(value = "page", defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(value = "size", defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size){

    }
}
