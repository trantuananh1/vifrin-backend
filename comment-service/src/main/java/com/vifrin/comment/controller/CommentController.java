package com.vifrin.comment.controller;

import com.vifrin.comment.service.CommentService;
import com.vifrin.common.constant.BaseConstant;
import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
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
    CommentService commentService;

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal Principal principal){
        CommentDto commentDto1 = commentService.addComment(commentDto, principal.getName());
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/comments/{id}")
                .buildAndExpand(commentDto1.getId()).toUri();
        return ResponseEntity
                .created(uri)
                .body(new ResponseTemplate<CommentDto>(ResponseType.SUCCESS, commentDto1));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateComment(@RequestBody CommentDto commentDto, @PathVariable Long id, @AuthenticationPrincipal Principal principal){
//        log.info("received a request to update a post with id {}", id);
//        postService.updatePost(postRequest, id, principal.getName());
//        return ResponseEntity
//                .ok(new ResponseTemplate<>(ResponseType.SUCCESS, null));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id, @AuthenticationPrincipal Principal principal){
        CommentDto commentDto = commentService.getComment(id, principal.getName());
        return ResponseEntity
                .ok(new ResponseTemplate<CommentDto>(ResponseType.SUCCESS, commentDto));
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<?> getCommentsByPost(@PathVariable Long postId, @AuthenticationPrincipal Principal principal,
                                               @RequestParam(value = "page", defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(value = "size", defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size){
        List<CommentDto> commentDtos = commentService.getComments(postId, principal.getName(), page, size);
        return commentDtos.isEmpty()?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(new ResponseTemplate<>(ResponseType.SUCCESS, commentDtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok(new ResponseTemplate<>(ResponseType.SUCCESS));
    }
}
