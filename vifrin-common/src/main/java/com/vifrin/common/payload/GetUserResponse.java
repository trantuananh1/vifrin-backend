package com.vifrin.common.payload;

import com.vifrin.common.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Collection;

/**
 * @Author: trantuananh1
 * @Created: Sun, 12/09/2021 9:49 PM
 **/

@Data
@Builder
public class GetUserResponse {
    private Long userId;
    private String username;
    private boolean isEnabled;
    private Instant createdAt;
    private Instant updatedAt;
    private Collection<Role> roles;
}