package com.vifrin.search.controller;

import com.vifrin.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: trantuananh1
 * @since: Sun, 19/12/2021
 **/

@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {
    @Autowired
    SearchService searchService;

    @GetMapping
    public ResponseEntity<?> search(@RequestParam String key, @AuthenticationPrincipal String username){
        Map<String, Object> result = searchService.search(key, username);
        return !result.isEmpty() ?
                ResponseEntity.ok(result) :
                ResponseEntity.noContent().build();
    }
}
