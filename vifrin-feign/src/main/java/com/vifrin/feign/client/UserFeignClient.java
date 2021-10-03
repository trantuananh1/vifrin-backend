package com.vifrin.feign.client;

import com.vifrin.common.entity.User;
import com.vifrin.common.payload.CreateUserResponse;
import com.vifrin.common.payload.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @Author: trantuananh1
 * @Created: Thu, 02/09/2021 2:30 PM
 **/

@FeignClient("USER-SERVICE")
public interface UserFeignClient {
    @PostMapping("/users")
    CreateUserResponse createUser(@RequestBody RegisterRequest registerRequest);

    @GetMapping("/users")
    Optional<User> findByUserName(@RequestParam("username") String userName);

    @GetMapping("/users")
    Optional<User> findByUserId(@RequestParam("userId") String userId);
}
