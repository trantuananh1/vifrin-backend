package com.vifrin.destination.controller;

import com.vifrin.common.config.constant.BaseConstant;
import com.vifrin.common.dto.DestinationDto;
import com.vifrin.common.payload.DestinationRequest;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import com.vifrin.destination.service.DestinationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Sun, 12/12/2021
 **/

@RestController
@RequestMapping("/destinations")
@Slf4j
public class DestinationController {
    @Autowired
    DestinationService destinationService;

    @PostMapping
    public ResponseEntity<?> createDestination(@RequestBody DestinationRequest destinationRequest){
        DestinationDto destinationDto1 = destinationService.createDestination(destinationRequest);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/destinations/{destinationId}")
                .buildAndExpand(destinationDto1.getId()).toUri();
        return ResponseEntity
                .created(uri)
                .body(new ResponseTemplate<DestinationDto>(ResponseType.SUCCESS, destinationDto1));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDestination(@PathVariable Long id, @RequestBody DestinationRequest destinationRequest){
        DestinationDto destinationDto = destinationService.updateDestination(id, destinationRequest);
        return ResponseEntity
                .ok(new ResponseTemplate<DestinationDto>(ResponseType.SUCCESS, destinationDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDestination(@PathVariable Long id){
        DestinationDto destinationDto = destinationService.getDestination(id);
        return ResponseEntity
                .ok(new ResponseTemplate<DestinationDto>(ResponseType.SUCCESS, destinationDto));
    }

    @GetMapping("/top-ranking")
    public ResponseEntity<?> getTopDestination(
            @RequestParam(defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size
    ){
        List<DestinationDto> destinationDtos = destinationService.getTopDestinations(page, size);
        return ResponseEntity
                .ok(new ResponseTemplate<>(ResponseType.SUCCESS, destinationDtos));
    }

//    @GetMapping("/comment-statistic")
//    public ResponseEntity<?> getCommentStatistic(
//    ){
//        List<DestinationDto> destinationDtos = destinationService.getTopDestinations();
//        return ResponseEntity
//                .ok(new ResponseTemplate<>(ResponseType.SUCCESS, destinationDtos));
//    }

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
    public ResponseEntity<List<DestinationDto>> search(@RequestParam String key){
        List<DestinationDto> destinationDtos = destinationService.search(key);
        return !destinationDtos.isEmpty() ?
                ResponseEntity.ok(destinationDtos) :
                ResponseEntity.noContent().build();
    }

    @GetMapping("random_destination")
    public ResponseEntity getRandomDestination( @RequestParam(value = "page", defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size){
        return ResponseEntity.ok(destinationService.getRandomDestinations(page, size));
    }

    @DeleteMapping("wrong_destination")
    public ResponseEntity removeWrongDestinations(){
        destinationService.removeWrongDestination();
        return ResponseEntity.ok().build();
    }
}
