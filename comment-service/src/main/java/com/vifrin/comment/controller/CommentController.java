package com.vifrin.comment.controller;

import com.vifrin.comment.service.CommentService;
import com.vifrin.common.config.constant.BaseConstant;
import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.dto.StatisticRatingDto;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * @author: tranmanhhung
 * @since: Sun, 12/12/2021
 **/

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "*")
@Slf4j
public class CommentController {
    @Autowired
    CommentService commentService;

    @MessageMapping("/comment.create")
    @SendTo("/topic/comment/{postId}")
    @PostMapping
    public ResponseEntity<?> addComment(@DestinationVariable Long postId, @RequestBody CommentDto commentDto, @AuthenticationPrincipal Principal principal){
        CommentDto commentDto1 = commentService.addComment(commentDto, principal.getName());
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/comments/{postId}")
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

    @MessageMapping("/comment.get")
    @SendTo("/topic/comment/{postId}")
    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id, @AuthenticationPrincipal Principal principal){
        CommentDto commentDto = commentService.getComment(id, principal.getName());
        return ResponseEntity
                .ok(new ResponseTemplate<CommentDto>(ResponseType.SUCCESS, commentDto));
    }

    @MessageMapping("/comment.get.all")
    @SendTo("/topic/comment/{postId}")
    @GetMapping("/by-post/{postId}")
    public ResponseEntity<?> getCommentsByPost(@PathVariable Long postId, @AuthenticationPrincipal Principal principal,
                                               @RequestParam(value = "page", defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(value = "size", defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size){
        List<CommentDto> commentDtos = commentService.getCommentsByPost(postId, principal.getName(), page, size);
        return commentDtos.isEmpty()?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(new ResponseTemplate<>(ResponseType.SUCCESS, commentDtos));
    }

    @GetMapping("/by-destination/{destinationId}")
    public ResponseEntity<?> getCommentsByDestination(@PathVariable Long destinationId, @AuthenticationPrincipal Principal principal,
                                                      @RequestParam(value = "page", defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(value = "size", defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size,
                                                      @RequestParam Optional<Integer> star){
        List<CommentDto> commentDtos =
                commentService.getCommentsByDestination(destinationId, principal.getName(), page, size, star);
        return commentDtos.isEmpty()?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(new ResponseTemplate<>(ResponseType.SUCCESS, commentDtos));
    }

    @GetMapping("/by-hotel/{hotelId}")
    public ResponseEntity<?> getCommentsByHotel(@PathVariable Long hotelId, @AuthenticationPrincipal Principal principal,
                                                      @RequestParam(value = "page", defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(value = "size", defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size,
                                                      @RequestParam Optional<Integer> star){
        List<CommentDto> commentDtos =
                commentService.getCommentsByHotel(hotelId, principal.getName(), page, size, star);
        return commentDtos.isEmpty()?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(new ResponseTemplate<>(ResponseType.SUCCESS, commentDtos));
    }

    @GetMapping("stats-by-hotel/{hotelId}")
    public ResponseEntity<?> getStatsRating(@PathVariable Long hotelId){
        StatisticRatingDto ratingDto =
                commentService.getStatRating(hotelId);
        return ratingDto == null?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(new ResponseTemplate<>(ResponseType.SUCCESS, ratingDto));
    }

    @GetMapping("stats-by-destination/{destinationId}")
    public ResponseEntity<?> getStatsRatingByDestinations(@PathVariable Long destinationId){
        StatisticRatingDto ratingDto =
                commentService.getStatRatingByDestination(destinationId);
        return ratingDto == null?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(new ResponseTemplate<>(ResponseType.SUCCESS, ratingDto));
    }

    @MessageMapping("/comment.delete")
    @SendTo("/topic/comment/{postId}")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok(new ResponseTemplate<>(ResponseType.SUCCESS));
    }
}
