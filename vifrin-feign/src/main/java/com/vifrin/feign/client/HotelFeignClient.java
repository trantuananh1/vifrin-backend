package com.vifrin.feign.client;

import com.vifrin.common.dto.HotelDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Wed, 29/06/2022
 **/

@FeignClient("HOTEL-SERVICE")
public interface HotelFeignClient {
    @GetMapping("/hotel/search")
    ResponseEntity<List<HotelDto>> search(@RequestParam String key);
}
