package com.vifrin.auth.exception;

import lombok.Getter;

/**
 * @author: trantuananh1
 * @since: Sun, 31/10/2021
 **/

public class SuccessCodeWithErrorResponse extends RuntimeException{

    @Getter
    private ErrorResponse errorResponse;

    @Getter
    private String id;

    public SuccessCodeWithErrorResponse(String id, ErrorResponse errorResponse) {
        this.id = id;
        this.errorResponse = errorResponse;
    }

    public SuccessCodeWithErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

}
