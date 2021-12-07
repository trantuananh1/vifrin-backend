package com.vifrin.post.controller;

import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import com.vifrin.post.service.UserService;
import com.vifrin.common.payload.UserDto;
import com.vifrin.common.payload.request.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired UserService userService;

    @PostMapping
    public ResponseEntity<ResponseTemplate<UserDto>> createUser(@RequestBody RegisterRequest registerRequest) {
        log.info("Inside createUser of UserController");
        UserDto userDto = userService.createUser(registerRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(userDto.getUsername()).toUri();
        return ResponseEntity
                .created(location)
                .body(new ResponseTemplate<UserDto>(ResponseType.CREATED, userDto));
    }

//    @GetMapping
//    public ResponseEntity<?> getUser(@RequestParam("username") Optional<String> username,
//                                        @RequestParam("userId") Optional<Long> userId){
//        UserDto user = null;
//        if (username.isPresent()){
//            user = userService.getUserByUserName(username.get());
//        } else if (userId.isPresent()){
////            user = userService.getUserByUserId(userId.get());
//        }
//        return ResponseEntity.ok(user);
//    }

    @GetMapping
    public ResponseEntity<ResponseTemplate> getUser(@RequestParam String username){
        UserDto user = null;
        user = userService.getUserByUserName(username);
        ResponseTemplate responseTemplate = new ResponseTemplate(ResponseType.OK, user);
        return ResponseEntity.ok().body(responseTemplate);
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

