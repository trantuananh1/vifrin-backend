package com.vifrin.feign.payload;

import lombok.Data;

/**
 * @Author: trantuananh1
 * @Created: Sun, 12/09/2021 9:49 PM
 **/

@Data
public class GetUserResponse {
    private String userName;
    private String password;
}
