package com.vifrin.feign.client;

import com.vifrin.common.payload.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: trantuananh1
 * @Created: Thu, 02/09/2021 4:05 PM
 **/

@FeignClient("AUTH-SERVICE")
public interface AuthFeignClient {
    @GetMapping("/auth/me")
    ResponseEntity<UserDto> getCurrentUser();
}
