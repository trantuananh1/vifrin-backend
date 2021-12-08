package com.vifrin.common.dto;

import lombok.Data;

/**
 * @author: trantuananh1
 * @since: Wed, 08/12/2021
 **/

@Data
public class FollowDto {
    private Long userId;
    private String username;
    private String fullName;
    private String avatarUrl;
    private boolean isFollow;
}
