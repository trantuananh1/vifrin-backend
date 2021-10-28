package com.snw.auth.payload;

import lombok.Data;

/**
 * @Author: trantuananh1
 * @Created: Thu, 09/09/2021 12:16 PM
 **/

@Data
public class LoginRequest {
    private String username;
    private String password;
}
