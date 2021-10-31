package com.vifrin.user.controller;

import com.vifrin.user.service.UserService;
import com.vifrin.common.payload.UserDto;
import com.vifrin.common.payload.request.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest registerRequest) {
//        log.info("Inside createUser of UserController");
        return ResponseEntity.ok(userService.createUser(registerRequest));
    }

    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam("username") Optional<String> username,
                                        @RequestParam("userId") Optional<Long> userId){
        UserDto user = null;
        if (username.isPresent()){
            user = userService.getUserByUserName(username.get());
        } else if (userId.isPresent()){
//            user = userService.getUserByUserId(userId.get());
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String token){
        UserDto userDto = userService.getCurrentUser(token);
        return ResponseEntity.ok(userDto);
    }

//
//    @PutMapping("/profile")
//    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest request){
//        return new ResponseEntity();
//    }
//    @GetMapping("/{id}")
//    public ResponseTemplateVO getUserWithDepartment(@PathVariable("id") Long userId) {
//        log.info("Inside getUserWithDepartment of UserController");
//        return userService.getUserWithDepartment(userId);
//    }

}

