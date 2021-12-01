package com.vifrin.auth.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: trantuananh1
 * @since: Sun, 31/10/2021
 **/

@Data
@NoArgsConstructor
public class UsernameAlreadyExistsException extends RuntimeException{
    private Error error;
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }

    public UsernameAlreadyExistsException(Error accountExistedError) {
    }
}
