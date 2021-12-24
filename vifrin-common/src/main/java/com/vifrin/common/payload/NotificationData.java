package com.vifrin.common.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Wed, 22/12/2021
 **/

@Data
@AllArgsConstructor
public class NotificationData {
    private int notificationType;
    private Long entityId;
    private Long actorId;
    private List<Long> notifierIds;
}
