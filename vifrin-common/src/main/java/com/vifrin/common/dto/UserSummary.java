package com.vifrin.common.dto;

import lombok.Data;

/**
 * @author: trantuananh1
 * @since: Sun, 12/12/2021
 **/

@Data
public class UserSummary {
    private Long id;
    private String username;
    private String fullName;
    private String avatarUrl;
}