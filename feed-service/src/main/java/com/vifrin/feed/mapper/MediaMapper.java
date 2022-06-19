package com.vifrin.feed.mapper;

import com.vifrin.common.dto.MediaDto;
import com.vifrin.common.entity.Media;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Sun, 12/12/2021
 **/

@Mapper(componentModel = "spring")
public abstract class MediaMapper {
    @Mapping(target = "id", source = "media.id")
    @Mapping(target = "url", source = "media.url")
    @Mapping(target = "name", source = "media.name")
    @Mapping(target = "mime", source = "media.mime")
    @Mapping(target = "width", source = "media.width")
    @Mapping(target = "height", source = "media.height")
    @Mapping(target = "size", source = "media.size")
    @Mapping(target = "createdAt", source = "media.createdAt")
    @Mapping(target = "postId", source = "media.post.id")
    @Mapping(target = "userId", source = "media.user.id")
    public abstract MediaDto mediaToMediaDto(Media media);

    public abstract List<MediaDto> mediasToMediaDtos(List<Media> media);
}
