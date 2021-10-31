package com.vifrin.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: trantuananh1
 * @since: Sun, 31/10/2021
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error {
    private String code;
    private String message;
}
