package com.vifrin.destination.controller;

import com.vifrin.common.constant.BaseConstant;
import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.dto.DestinationDto;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import com.vifrin.destination.service.DestinationService;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.provider.symmetric.DES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

/**
 * @author: trantuananh1
 * @since: Sun, 12/12/2021
 **/

@RestController
@RequestMapping("/destinations")
@Slf4j
public class DestinationController {
    @Autowired
    DestinationService destinationService;

    @PostMapping
    public ResponseEntity<?> createDestination(@RequestBody DestinationDto destinationDto){
        DestinationDto destinationDto1 = destinationService.createDestination(destinationDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/destinations/{destinationId}")
                .buildAndExpand(destinationDto1.getId()).toUri();
        return ResponseEntity
                .created(uri)
                .body(new ResponseTemplate<DestinationDto>(ResponseType.SUCCESS, destinationDto1));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDestination(@PathVariable Long id){
        DestinationDto destinationDto = destinationService.getDestination(id);
        return ResponseEntity
                .ok(new ResponseTemplate<DestinationDto>(ResponseType.SUCCESS, destinationDto));
    }

    @GetMapping
    public ResponseEntity<?> getAllDestination(){
        List<DestinationDto> destinationDtos = destinationService.getAllDestination();
        return ResponseEntity
                .ok(new ResponseTemplate<>(ResponseType.SUCCESS, destinationDtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDestination(@PathVariable Long id){
        destinationService.deleteDestination(id);
        return ResponseEntity.ok(new ResponseTemplate<>(ResponseType.SUCCESS));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String key){
        List<DestinationDto> destinationDtos = destinationService.search(key);
        return ResponseEntity
                .ok(new ResponseTemplate<>(ResponseType.SUCCESS, destinationDtos));
    }
}
