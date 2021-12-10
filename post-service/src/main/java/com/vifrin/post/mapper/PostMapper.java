package com.vifrin.post.mapper;

import com.vifrin.common.entity.Post;
import com.vifrin.common.entity.User;
import com.vifrin.common.payload.UserDto;
import com.vifrin.common.payload.post.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Sun, 05/12/2021
 **/

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Mapping(target = "content", source = "post.content")
    @Mapping(target = "imageUrl", source = "post.imageUrl")
    @Mapping(target = "hasDetail", source = "post.hasDetail")
    @Mapping(target = "userId", source = "post.userId")
    @Mapping(target = "detail", source = "post.detail")
    @Mapping(target = "createdAt", source = "post.createdAt")
    @Mapping(target = "updatedAt", source = "post.updatedAt")
    @Mapping(target = "config", source = "post.config")
    public abstract PostDto postToPostDto(Post post);

    public abstract List<PostDto> postsToPostDtos(List<Post> posts);
}
