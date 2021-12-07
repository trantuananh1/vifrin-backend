package com.vifrin.post.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: trantuananh1
 * @since: Sun, 31/10/2021
 **/

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource) {
        super(String.format("Resource %s not found", resource));
    }
}
