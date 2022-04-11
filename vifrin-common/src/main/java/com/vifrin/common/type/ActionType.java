package com.vifrin.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: trantuananh1
 * @since: Thu, 23/12/2021
 **/

@Getter
@AllArgsConstructor
public enum ActionType {
    ADD(1, "add"),
    UPDATE(2, "update"),
    DELETE(3, "delete"),
    COMMENT(4, "comment"),
    LIKE(5, "like"),
    FOLLOW(6, "follow");

    private int value;
    private String name;
}
