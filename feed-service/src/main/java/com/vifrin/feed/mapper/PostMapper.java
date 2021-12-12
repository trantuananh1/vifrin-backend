package com.vifrin.feed.mapper;

import com.vifrin.common.dto.MediaDto;
import com.vifrin.common.entity.Media;
import com.vifrin.common.entity.Post;
import com.vifrin.common.dto.PostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Sun, 05/12/2021
 **/

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    MediaMapper mediaMapper;

    @Mapping(target = "content", source = "post.content")
    @Mapping(target = "userId", source = "post.user.id")
    @Mapping(target = "createdAt", source = "post.createdAt")
    @Mapping(target = "updatedAt", source = "post.updatedAt")
    @Mapping(target = "config", source = "post.config")
    @Mapping(target = "likesCount", source = "post.activity.likesCount")
    @Mapping(target = "commentsCount", source = "post.activity.commentsCount")
    @Mapping(target = "medias", expression = "java(getMedias(post))")
    public abstract PostDto postToPostDto(Post post);

    public abstract List<PostDto> postsToPostDtos(List<Post> posts);

    List<MediaDto> getMedias(Post post){
        List<Media> medias = post.getMedias();
        return mediaMapper.mediasToMediaDtos(medias);
    }
}

