package com.snw.user.controller;

import com.snw.user.service.UserService;
import com.vifrin.common.entity.User;
import com.vifrin.common.payload.GetUserResponse;
import com.vifrin.common.payload.RoleToUserForm;
import com.vifrin.common.payload.CreateUserResponse;
import com.vifrin.common.payload.RegisterRequest;
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
    public CreateUserResponse createUser(@RequestBody RegisterRequest registerRequest) {
//        log.info("Inside createUser of UserController");
        return userService.createUser(registerRequest);
    }

    @GetMapping
    public ResponseEntity<GetUserResponse> getUser(@RequestParam("username") Optional<String> username,
                                        @RequestParam("userId") Optional<Long> userId){
        GetUserResponse user = null;
        if (username.isPresent()){
            user = userService.getUserByUserName(username.get());
        } else if (userId.isPresent()){
            user = userService.getUserByUserId(userId.get());
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/role")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
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

