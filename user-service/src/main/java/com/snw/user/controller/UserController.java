package com.snw.user.controller;

import com.snw.user.VO.ResponseTemplateVO;
import com.snw.user.entity.User;
import com.snw.user.payload.UpdateProfileRequest;
import com.snw.user.service.UserService;
import com.vifrin.feign.payload.CreateUserResponse;
import com.vifrin.feign.payload.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired UserService userService;

    @PostMapping
    public CreateUserResponse createUser(@RequestBody RegisterRequest registerRequest) {
        log.info("Inside createUser of UserController");
        return userService.createUser(registerRequest);
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
