package com.vifrin.feign.client;

import com.vifrin.common.payload.UserDto;
import com.vifrin.common.payload.response.CreateUserResponse;
import com.vifrin.common.payload.request.RegisterRequest;
import com.vifrin.common.response.ResponseTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: trantuananh1
 * @Created: Thu, 02/09/2021 2:30 PM
 **/

@FeignClient("USER-SERVICE")
public interface UserFeignClient {
    @PostMapping("/users")
    ResponseEntity<ResponseTemplate<UserDto>> createUser(@RequestBody RegisterRequest registerRequest);

    @GetMapping("/users")
    ResponseEntity<ResponseTemplate> getUser(@RequestParam String username);

    @GetMapping("/users/me")
    ResponseEntity<String> getCurrent();

    @GetMapping("/users/{userId}/followings")
    ResponseEntity<?> getFollowings(@PathVariable Long userId);
}
