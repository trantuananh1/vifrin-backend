package com.vifrin.feign.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author: tranmanhhung
 * @since: Sun, 12/12/2021
 **/

@FeignClient("MEDIA-SERVICE")
public interface MediaFeignClient {
}
