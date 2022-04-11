package com.vifrin.common.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author: trantuananh1
 * @since: Thu, 23/12/2021
 **/

@Getter
@AllArgsConstructor
public enum NotificationType {
    ADD_POST(0, EntityType.POST, ActionType.ADD, "%s added a post. %s"),
    COMMENT_POST(1, EntityType.POST, ActionType.COMMENT,"%s commented to a post of you. %s"),
    LIKE_POST(2, EntityType.POST, ActionType.LIKE, "%s liked a post of you. %s"),
    FOLLOW(3, EntityType.USER, ActionType.FOLLOW, "%s started following you.");
//
//    COMMENT_ON_POST(20, "comment to a post"),
//    COMMENT_ON_DESTINATION(21, "comment to a post"),
//
//    LIKE_POST(20, "comment to a post"),
//    LIKE_COMMENT(21, "comment to a post"),
//
//    FOLLOW(40, "follow");

    private final int value;
    private final EntityType entityType;
    private final ActionType actionType;
    private final String template;

    public static NotificationType[] getNotificationTypes(){
        return new NotificationType[]{ADD_POST, COMMENT_POST, LIKE_POST, FOLLOW};
    }
    public static NotificationType getByValue(int value){
        return getNotificationTypes()[value];
    }
}
