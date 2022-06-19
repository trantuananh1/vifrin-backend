package com.vifrin.hotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: tranmanhhung
 * @since: Sun, 31/10/2021
 **/

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource) {
        super(String.format("Resource %s not found", resource));
    }

    public ResourceNotFoundException(Long id) {
        super(String.format("Resource %s not found", id));
    }
}
