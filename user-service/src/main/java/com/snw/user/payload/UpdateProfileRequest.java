package com.snw.user.payload;

import com.snw.user.entity.Gender;
import lombok.Data;

/**
 * @Author: trantuananh1
 * @Created: Sun, 12/09/2021 10:29 PM
 **/

@Data
public class UpdateProfileRequest {
    String fullName;
    String username;
    String bio;
    String email;
    String phoneNumber;
    Gender gender;
}
