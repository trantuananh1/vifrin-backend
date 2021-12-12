package com.vifrin.comment.mapper;

import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.dto.PostDto;
import com.vifrin.common.entity.Comment;
import com.vifrin.common.entity.Post;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Sun, 12/12/2021
 **/

public abstract class CommentMapper {
    @Mapping(target = "id", source = "comment.id")
    @Mapping(target = "content", source = "comment.content")
    @Mapping(target = "createdAt", source = "comment.createdAt")
    @Mapping(target = "updatedAt", source = "comment.updatedAt")
    @Mapping(target = "userId", source = "comment.user.id")
    @Mapping(target = "postId", source = "comment.user.id")
    @Mapping(target = "likesCount", source = "comment.activity.likesCount")
    public abstract CommentDto commentToCommentDto(Comment comment);

    public abstract List<CommentDto> commentsToCommentDtos(List<Comment> comments);
}
