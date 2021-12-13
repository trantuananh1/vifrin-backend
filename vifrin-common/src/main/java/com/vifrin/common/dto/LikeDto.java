package com.vifrin.common.dto;

import lombok.Data;

import java.time.Instant;

/**
 * @author: trantuananh1
 * @since: Mon, 13/12/2021
 **/

@Data
public class LikeDto {
    private Long id;
    private Long postId;
    private int type;
    private Instant createdAt;
    private UserSummary user;
}
