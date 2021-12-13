package com.vifrin.comment.mapper;

import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.dto.PostDto;
import com.vifrin.common.dto.UserSummary;
import com.vifrin.common.entity.Comment;
import com.vifrin.common.entity.Post;
import com.vifrin.common.entity.User;
import com.vifrin.feign.client.UserFeignClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Sun, 12/12/2021
 **/

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Autowired
    UserFeignClient userFeignClient;

    @Mapping(target = "id", source = "comment.id")
    @Mapping(target = "content", source = "comment.content")
    @Mapping(target = "createdAt", source = "comment.createdAt")
    @Mapping(target = "updatedAt", source = "comment.updatedAt")
    @Mapping(target = "postId", source = "comment.user.id")
    @Mapping(target = "likesCount", source = "comment.activity.likesCount")
    @Mapping(target = "user", expression = "java(getUserSummary(comment))")
    public abstract CommentDto commentToCommentDto(Comment comment);

    public abstract List<CommentDto> commentsToCommentDtos(List<Comment> comments);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", source = "commentDto.content")
    @Mapping(target = "createdAt",expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "activity", expression = "java(new Activity())")
    public abstract Comment commentDtoToComment(CommentDto commentDto, Post post, User user);

    public abstract List<Comment> commentDtosToComments(List<CommentDto> commentDtos);

    UserSummary getUserSummary(Comment comment){
        return userFeignClient.getUserSummary(comment.getUser().getId()).getBody();
    }
}