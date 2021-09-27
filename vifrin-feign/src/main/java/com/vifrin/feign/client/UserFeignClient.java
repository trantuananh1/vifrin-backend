package com.vifrin.feign.client;

import com.vifrin.common.entity.User;
import com.vifrin.feign.payload.CreateUserResponse;
import com.vifrin.feign.payload.GetUserResponse;
import com.vifrin.feign.payload.RegisterRequest;
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
    Optional<User> getUserByUserName(@RequestParam("userName") String userName);

    @GetMapping("/users")
    GetUserResponse getUserByUserId(@RequestParam("userId") String userId);
}
