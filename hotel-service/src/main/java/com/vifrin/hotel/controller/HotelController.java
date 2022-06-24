package com.vifrin.hotel.controller;

import com.vifrin.common.config.constant.BaseConstant;
import com.vifrin.common.dto.DestinationDto;
import com.vifrin.common.dto.HotelDto;
import com.vifrin.common.entity.Hotel;
import com.vifrin.common.payload.DestinationRequest;
import com.vifrin.common.payload.HotelRequest;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import com.vifrin.hotel.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Sun, 12/12/2021
 **/

@RestController
@RequestMapping("/hotel")
@Slf4j
public class HotelController {
    @Autowired
    HotelService hotelService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody HotelRequest hotelRequest) {
        HotelDto hotelDto = hotelService.createHotel(hotelRequest);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/destinations/{destinationId}")
                .buildAndExpand(hotelDto.getId()).toUri();
        return ResponseEntity
                .created(uri)
                .body(new ResponseTemplate<HotelDto>(ResponseType.SUCCESS, hotelDto));
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<?> getHotel(@PathVariable Long hotelId) {
        return ResponseEntity
                .ok(new ResponseTemplate<>(ResponseType.SUCCESS, hotelService.getHotel(hotelId)));
    }

    @GetMapping("/get-by-destination")
    public ResponseEntity<?> getByDestination(@RequestParam Long destinationId,
                                              @RequestParam(defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
                                              @RequestParam(defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size) {
        List<HotelDto> hotelsDto = hotelService.findByDestinationId(destinationId, page, size);
        return ResponseEntity
                .ok(new ResponseTemplate<>(ResponseType.SUCCESS, hotelsDto));
    }

    @GetMapping("/get-top-hotel-by-destination")
    public ResponseEntity<?> getTopHotelByDestination(@RequestParam Long destinationId,
                                                      @RequestParam(defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size) {
        List<HotelDto> hotelsDto = hotelService.findByDestinationId(destinationId, page, size);
        Collections.sort(hotelsDto, new Comparator<HotelDto>() {
            @Override
            public int compare(HotelDto o1, HotelDto o2) {
                return Float.compare(o1.getAverageScore(), o2.getAverageScore());
            }
        }.reversed());

        return ResponseEntity
                .ok(new ResponseTemplate<>(ResponseType.SUCCESS, hotelsDto));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<HotelDto> hotelsDto = hotelService.getAllHotel();
        return ResponseEntity
                .ok(new ResponseTemplate<>(ResponseType.SUCCESS, hotelsDto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelDto>> search(@RequestParam String key) {
        List<HotelDto> hotelsDto = hotelService.search(key);
        return ResponseEntity.ok(hotelsDto);
//        return !hotelsDto.isEmpty() ?
//                ResponseEntity.ok(hotelsDto) :
//                ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        HotelDto deletedHotel = hotelService.deleteHotel(id);
        return ResponseEntity.ok(new ResponseTemplate<>(ResponseType.SUCCESS, deletedHotel));
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAll() {
        hotelService.deleteAllHotels();
        return ResponseEntity.ok(new ResponseTemplate<>(ResponseType.SUCCESS));
    }
}
