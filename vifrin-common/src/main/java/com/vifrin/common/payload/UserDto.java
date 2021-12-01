package com.vifrin.common.payload;

import com.vifrin.common.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    private Long userId;
    private String username;
    private String email;
    private boolean isEnabled;
    private Instant createdAt;
    private Instant updatedAt;
    private String role;
}
