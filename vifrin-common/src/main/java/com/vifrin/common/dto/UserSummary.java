package com.vifrin.common.dto;

import lombok.Data;

/**
 * @author: tranmanhhung
 * @since: Sun, 12/12/2021
 **/

@Data
public class UserSummary {
    private Long id;
    private String username;
    private String fullName;
    private String avatarUrl;
    private boolean isFollowing;
    private boolean isFollower;
}
