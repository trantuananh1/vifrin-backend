package com.vifrin.feign.client;

import com.vifrin.common.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: trantuananh1
 * @Created: Thu, 02/09/2021 4:05 PM
 **/

@FeignClient("AUTH-SERVICE")
public interface AuthFeignClient {
}
