package com.vifrin.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: tranmanhhung
 * @since: Sun, 31/10/2021
 **/

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource) {
        super(String.format("Resource %s not found", resource));
    }

}
