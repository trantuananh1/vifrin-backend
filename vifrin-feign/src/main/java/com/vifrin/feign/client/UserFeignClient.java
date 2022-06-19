package com.vifrin.feign.client;

import com.vifrin.common.dto.FollowDto;
import com.vifrin.common.dto.UserDto;
import com.vifrin.common.dto.UserSummary;
import com.vifrin.common.payload.RegisterRequest;
import com.vifrin.common.response.ResponseTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author: tranmanhhung
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

    @GetMapping("/users/{userId}/followers")
    ResponseEntity<List<FollowDto>> getFollowers(@PathVariable Long userId, @RequestParam int page, @RequestParam int size, @RequestHeader("Authorization") String token);

    @GetMapping("/users/{userId}/followers")
    ResponseEntity<List<FollowDto>> getFollowers(@PathVariable Long userId, @RequestParam int page, @RequestParam int size);

    @GetMapping("/users/{userId}/followings")
    ResponseEntity<List<FollowDto>> getFollowings(@PathVariable Long userId, @RequestParam int page, @RequestParam int size, @RequestHeader("Authorization") String token);

    @GetMapping("/users/summary/in")
    ResponseEntity<List<UserSummary>> getUserSummaries(@RequestBody List<Long> ids, @RequestHeader("Authorization") String token);

    @GetMapping("/users/summary/{userId}")
    ResponseEntity<UserSummary> getUserSummary(@PathVariable Long userId, @RequestHeader("Authorization") String token);

    @GetMapping("/users/search")
    ResponseEntity<List<UserSummary>> search(@RequestParam String key, @RequestHeader("Authorization") Optional<String> token);
}
