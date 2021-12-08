package com.vifrin.post.controller;

import com.vifrin.common.constant.StringPool;
import com.vifrin.common.entity.Post;
import com.vifrin.common.payload.post.PostDto;
import com.vifrin.common.payload.post.PostRequest;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import com.vifrin.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

/**
 * @author: trantuananh1
 * @since: Sun, 05/12/2021
 **/

@RestController
@RequestMapping("/posts")
@Slf4j
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest, @AuthenticationPrincipal Principal principal){
        log.info("received a request to create a post for image {}", postRequest.getImageUrl());
        PostDto postDto = postService.createPost(postRequest, principal.getName());
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/posts/{id}")
                .buildAndExpand(postDto.getId()).toUri();
        return ResponseEntity
                .created(uri)
                .body(new ResponseTemplate<PostDto>(ResponseType.CREATED, postDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@RequestBody PostRequest postRequest, @PathVariable Long id, @AuthenticationPrincipal Principal principal){
        log.info("received a request to update a post with id {}", id);
        postService.updatePost(postRequest, id, principal.getName());
        return ResponseEntity
                .ok(new ResponseTemplate<>(ResponseType.OK, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id, @AuthenticationPrincipal Principal user){
        log.info("received a request to get a post with id {}", id);
        PostDto postDto = postService.getPost(id);
        return ResponseEntity
                .ok(new ResponseTemplate<PostDto>(ResponseType.OK, postDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, @AuthenticationPrincipal Principal user){
        if (user == null){
            return ResponseEntity.badRequest().body(StringPool.BLANK);
        }
        log.info("received a delete request for post id {} from user {}", id, user.getName());
        postService.deletePost(id, user.getName());
        return ResponseEntity
                .ok(new ResponseTemplate<>(ResponseType.OK, null));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyPosts(@AuthenticationPrincipal Principal principal) {
        log.info("retrieving posts for user {}", principal.getName());
        List<PostDto> postDtos = postService.getPostsByUsername(principal.getName());
        log.info("found {} posts for user {}", postDtos.size(), principal.getName());
        if (postDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity
                .ok(new ResponseTemplate<List<PostDto>>(ResponseType.OK, postDtos));
    }

    @GetMapping
    public ResponseEntity<?> getPostsByUsername(@RequestParam String username) {
        log.info("retrieving posts for user {}", username);
        List<PostDto> postDtos = postService.getPostsByUsername(username);
        log.info("found {} posts for user {}", postDtos.size(), username);
        if (postDtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity
                .ok(new ResponseTemplate<List<PostDto>>(ResponseType.OK, postDtos));
    }
//
//    @PostMapping("/posts/in")
//    public ResponseEntity<?> findPostsByIdIn(@RequestBody List<String> ids) {
//        log.info("retrieving posts for {} ids", ids.size());
//
//        List<Post> posts = postService.postsByIdIn(ids);
//        log.info("found {} posts", posts.size());
//
//        return ResponseEntity.ok(posts);
//    }
}
