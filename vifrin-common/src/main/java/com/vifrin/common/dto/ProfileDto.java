package com.vifrin.common.dto;

import com.vifrin.common.entity.Gender;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * @author: trantuananh1
 * @since: Wed, 08/12/2021
 **/

@Data
public class ProfileDto {
    private Long userId;
    private String email;
    private String phoneNumber;
    private String bio;
    private Gender gender;
    private String fullName;
    private LocalDate dateOfBirth;
    private String country;
    private String avatarUrl;
}
