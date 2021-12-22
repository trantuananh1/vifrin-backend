package com.vifrin.common.payload;

import lombok.Data;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Wed, 22/12/2021
 **/

@Data
public class NotificationData {
    private int notificationType;
    private int entityId;
    private int actorId;
    private List<Long> notifierIds;
}
