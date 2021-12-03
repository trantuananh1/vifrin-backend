package com.vifrin.user.exception;

import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleCustomException(UsernameAlreadyExistsException ex){
        ResponseTemplate responseTemplate = new ResponseTemplate(ResponseType.USERNAME_ALREADY_EXISTS, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseTemplate);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleCustomException(EmailAlreadyExistsException ex){
        ResponseTemplate responseTemplate = new ResponseTemplate(ResponseType.EMAIL_ALREADY_EXISTS, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseTemplate);
    }

    @ExceptionHandler(UsernameNotExistsException.class)
    public ResponseEntity<?> handleCustomException(UsernameNotExistsException ex){
        ResponseTemplate responseTemplate = new ResponseTemplate(ResponseType.USERNAME_NOT_EXISTS, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseTemplate);
    }
}
