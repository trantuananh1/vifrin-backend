package com.vifrin.mediaservice.utils;

public class VifrinBaseException extends Exception {
    private static final long serialVersionUID = 1L;

    private ErrorInfo error;

    public VifrinBaseException(Throwable cause) {
        super(cause);
        this.setError(ErrorInfo.INTERNAL_SERVER_ERROR);
    }

    public VifrinBaseException(ErrorInfo error) {
        this.setError(error);
    }

    public ErrorInfo getError() {
        return error;
    }

    public void setError(ErrorInfo error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        if(error != null) return error.getMessage();
        return super.getMessage();
    }
}
