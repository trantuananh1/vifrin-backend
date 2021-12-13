package com.vifrin.post.exception;

import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<?> handleCustomException(NotAllowedException ex){
        ResponseTemplate responseTemplate = new ResponseTemplate(403, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(responseTemplate);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleCustomException(ResourceNotFoundException ex){
        ResponseTemplate responseTemplate = new ResponseTemplate(404, ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseTemplate);
    }
}
