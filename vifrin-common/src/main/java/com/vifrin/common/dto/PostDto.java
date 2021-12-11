package com.vifrin.common.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * @author: trantuananh1
 * @since: Mon, 06/12/2021
 **/

@Data
@Builder
public class PostDto {
    private Long id;
    private Long userId;
    private String content;
    private String imageUrl;
    private boolean hasDetail;
    private String detail;
    private Instant createdAt;
    private Instant updatedAt;
    private String config;
}
