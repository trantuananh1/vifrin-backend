package com.vifrin.common.dto;

import lombok.Data;

/**
 * @author: trantuananh1
 * @since: Thu, 23/12/2021
 **/

@Data
public class NotificationDto {
    private UserSummary user;
    private String content;
    private String createdAt;
}
