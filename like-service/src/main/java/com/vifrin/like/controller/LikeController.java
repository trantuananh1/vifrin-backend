package com.vifrin.like.controller;

import com.vifrin.common.config.constant.BaseConstant;
import com.vifrin.common.dto.LikeDto;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import com.vifrin.like.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
        return likeService.addLike(likeDto, principal.getName()) ?
                ResponseEntity.ok(new ResponseTemplate<>(ResponseType.SUCCESS, null)) :
                ResponseEntity.badRequest().body(new ResponseTemplate<>(ResponseType.CANNOT_LIKE));
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<?> getLikes(@PathVariable Long postId, @AuthenticationPrincipal Principal principal,
                                     @RequestParam(value = "page", defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
                                     @RequestParam(value = "size", defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size){
        List<LikeDto> likeDtos = likeService.getLikes(postId, principal.getName(), page, size);
        return likeDtos.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(new ResponseTemplate<List<LikeDto>>(ResponseType.SUCCESS, likeDtos));
    }
}
