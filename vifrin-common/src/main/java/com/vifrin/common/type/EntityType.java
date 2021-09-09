package com.vifrin.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: trantuananh1
 * @Created: Thu, 02/09/2021 3:25 PM
 **/

@Getter
@AllArgsConstructor
public enum EntityType {
    USER(1, "User"),
    POST(2, "Post"),
    COMMENT(3, "Comment"),
    TAG(4, "Tag"),
    HASHTAG(5, "Hashtag"),
    REACTION(6, "Reaction"),
    PROFILE(7, "Profile"),
    ATTACHMENT(8, "Attachment");

    private final int value;
    private final String name;
}