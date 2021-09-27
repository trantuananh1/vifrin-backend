package com.vifrin.feign.payload;

import lombok.Data;

/**
 * @Author: trantuananh1
 * @Created: Sun, 12/09/2021 5:13 PM
 **/

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private String email;
}
