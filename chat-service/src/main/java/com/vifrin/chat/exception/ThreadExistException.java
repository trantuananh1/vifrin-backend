package com.vifrin.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author: tranmanhhung
 * @since: Tue, 03/05/2022
 **/

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ThreadExistException extends RuntimeException{
    public ThreadExistException(){
        super("thread is exits");
    }
}
