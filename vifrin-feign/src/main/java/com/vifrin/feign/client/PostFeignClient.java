package com.vifrin.feign.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: tranmanhhung
 * @Created: Thu, 02/09/2021 2:30 PM
 **/

@FeignClient("POST-SERVICE")
public interface PostFeignClient {
}
