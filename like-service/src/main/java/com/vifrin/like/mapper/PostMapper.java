package com.vifrin.post.mapper;

import com.vifrin.common.dto.MediaDto;
import com.vifrin.common.dto.UserSummary;
import com.vifrin.common.entity.Media;
import com.vifrin.common.entity.Post;
import com.vifrin.common.dto.PostDto;
import com.vifrin.feign.client.UserFeignClient;
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
    @Autowired
    UserFeignClient userFeignClient;

    @Mapping(target = "content", source = "post.content")
    @Mapping(target = "userId", source = "post.user.id")
    @Mapping(target = "createdAt", source = "post.createdAt")
    @Mapping(target = "updatedAt", source = "post.updatedAt")
    @Mapping(target = "config", source = "post.config")
    @Mapping(target = "likesCount", source = "post.activity.likesCount")
    @Mapping(target = "commentsCount", source = "post.activity.commentsCount")
    @Mapping(target = "medias", expression = "java(getMedias(post))")
    @Mapping(target = "user", expression = "java(getUserSummary(post))")
    public abstract PostDto postToPostDto(Post post);

    public abstract List<PostDto> postsToPostDtos(List<Post> posts);

    List<MediaDto> getMedias(Post post){
        List<Media> medias = post.getMedias();
        return mediaMapper.mediasToMediaDtos(medias);
    }

    UserSummary getUserSummary(Post post){
        return userFeignClient.getUserSummary(post.getUser().getId()).getBody();
    }
}
