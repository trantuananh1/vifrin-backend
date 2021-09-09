package com.vifrin.feign.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: trantuananh1
 * @Created: Thu, 02/09/2021 4:05 PM
 **/

@FeignClient("AUTH-SERVICE")
public interface AuthFeignClient {
}
