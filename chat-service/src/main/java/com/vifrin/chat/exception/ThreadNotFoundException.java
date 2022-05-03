package com.vifrin.chat.exception;

/**
 * @author: trantuananh1
 * @since: Tue, 03/05/2022
 **/

public class ThreadNotFoundException extends RuntimeException {


    public ThreadNotFoundException() {
        super("thread not found");
    }
}
