package com.vifrin.common.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Mon, 06/12/2021
 **/

@Data
@Builder
public class PostDto {
    private Long id;
    private Long userId;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
    private String config;
    private int likesCount;
    private int commentsCount;
    private List<MediaDto> medias;
    private DestinationDto destination;
    private UserSummary user;
}
