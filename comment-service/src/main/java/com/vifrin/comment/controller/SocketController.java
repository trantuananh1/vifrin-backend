package com.vifrin.comment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: trantuananh1
 * @since: Wed, 22/12/2021
 **/

@RestController
public class SocketController {
    @GetMapping("/info")
    public ResponseEntity<?> testSocket(){
        return ResponseEntity.ok().build();
    }
}
