package com.vifrin.common.payload.response;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: trantuananh1
 * @Created: Sun, 12/09/2021 5:11 PM
 **/

@Data
@Builder
public class CreateUserResponse {
    private long userId;
    private String username;
}
