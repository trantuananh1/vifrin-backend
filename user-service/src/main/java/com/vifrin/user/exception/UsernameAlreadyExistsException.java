package com.vifrin.user.exception;

import com.vifrin.common.response.ResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: trantuananh1
 * @since: Sun, 31/10/2021
 **/

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException() {
        super();
    }
}
