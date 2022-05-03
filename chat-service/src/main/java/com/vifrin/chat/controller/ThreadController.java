package com.vifrin.chat.controller;

import com.vifrin.chat.dto.ThreadDto;
import com.vifrin.chat.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author: trantuananh1
 * @since: Tue, 03/05/2022
 **/

@RestController
@RequestMapping("threads")
@CrossOrigin(origins = "*")
@Slf4j
public class ThreadController {
    @Autowired
    ThreadService threadService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ThreadDto createThread(@RequestBody ThreadDto threadDto) {
        return threadService.createThread(threadDto);
    }

    @GetMapping
    public ThreadDto getThread(@RequestParam long userIdOne, @RequestParam long userIdTwo){
        return threadService.getThread(userIdOne, userIdTwo);
    }
}
