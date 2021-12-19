package com.vifrin.post.mapper;

import com.vifrin.common.dto.DestinationDto;
import com.vifrin.common.dto.MediaDto;
import com.vifrin.common.dto.UserSummary;
import com.vifrin.common.entity.Destination;
import com.vifrin.common.entity.Media;
import com.vifrin.common.entity.Post;
import com.vifrin.common.dto.PostDto;
import com.vifrin.common.util.RedisUtil;
import com.vifrin.feign.client.UserFeignClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: trantuananh1
 * @since: Sun, 05/12/2021
 **/

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    MediaMapper mediaMapper;
    @Autowired
    DestinationMapper destinationMapper;
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
    @Mapping(target = "destination", expression = "java(getDestination(post))")
    @Mapping(target = "user", expression = "java(getUserSummary(post, token))")
    public abstract PostDto postToPostDto(Post post, String token);

    public List<PostDto> postsToPostDtos(List<Post> posts, String token){
        return posts.stream()
                .map(post -> postToPostDto(post, token))
                .collect(Collectors.toList());
    }

    List<MediaDto> getMedias(Post post){
        List<Media> medias = post.getMedias();
        return mediaMapper.mediasToMediaDtos(medias);
    }

    DestinationDto getDestination(Post post){
        Destination destination = post.getDestination();
        return destinationMapper.destinationToDestinationDto(destination);
    }

    UserSummary getUserSummary(Post post, String token){
        return userFeignClient.getUserSummary(post.getUser().getId(), token).getBody();
    }
}
