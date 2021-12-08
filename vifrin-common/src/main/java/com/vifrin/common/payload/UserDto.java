package com.vifrin.common.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String email;
    private boolean isEnabled;
    private Instant createdAt;
    private Instant updatedAt;
    private String role;
    private String avatarUrl;
}
