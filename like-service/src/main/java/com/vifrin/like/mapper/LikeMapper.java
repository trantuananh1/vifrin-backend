package com.vifrin.like.mapper;

import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.dto.LikeDto;
import com.vifrin.common.dto.MediaDto;
import com.vifrin.common.dto.UserSummary;
import com.vifrin.common.entity.*;
import com.vifrin.feign.client.UserFeignClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: trantuananh1
 * @since: Mon, 13/12/2021
 **/

@Mapper(componentModel = "spring")
public abstract class LikeMapper {
    @Autowired
    UserFeignClient userFeignClient;

    @Mapping(target = "id", source = "like.id")
    @Mapping(target = "createdAt", source = "like.createdAt")
    @Mapping(target = "user", expression = "java(getUserSummary(like, token))")
    public abstract LikeDto likeToLikeDto(Like like, String token);

    public List<LikeDto> likesToLikeDtos(List<Like> likes, String token){
        return likes.stream()
                .map(like -> likeToLikeDto(like, token))
                .collect(Collectors.toList());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", constant = "0")
    @Mapping(target = "createdAt",expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "post", source = "post")
    public abstract Like likeDtoToLike(LikeDto likeDto, Post post, User user);

    public abstract List<Like> likeDtosToLikes(List<LikeDto> likeDtos);

    UserSummary getUserSummary(Like like, String token){
        return userFeignClient.getUserSummary(like.getUser().getId(), token).getBody();
    }
}
