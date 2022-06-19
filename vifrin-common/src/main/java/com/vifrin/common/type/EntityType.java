package com.vifrin.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: tranmanhhung
 * @Created: Thu, 02/09/2021 3:25 PM
 **/

@Getter
@AllArgsConstructor
public enum EntityType {
    USER(1, "user"),
    POST(2, "post"),
    COMMENT(3, "comment"),
    TAG(4, "tag"),
    HASHTAG(5, "hashtag"),
    PROFILE(7, "profile"),
    MEDIA(8, "media");

    private final int value;
    private final String name;
}