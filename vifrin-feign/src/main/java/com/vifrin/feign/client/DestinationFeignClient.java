package com.vifrin.feign.client;

import com.vifrin.common.dto.DestinationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Mon, 20/12/2021
 **/

@FeignClient("DESTINATION-SERVICE")
public interface DestinationFeignClient {
    @GetMapping("/destinations/search")
    ResponseEntity<List<DestinationDto>> search(@RequestParam String key);
}
