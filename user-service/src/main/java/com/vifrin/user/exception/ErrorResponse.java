package com.vifrin.user.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * @author: trantuananh1
 * @since: Sun, 31/10/2021
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private UUID uuid;
    private List<Error> errors;
}
