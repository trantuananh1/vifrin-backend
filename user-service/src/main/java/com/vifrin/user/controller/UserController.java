package com.vifrin.user.controller;

import com.vifrin.common.constant.BaseConstant;
import com.vifrin.common.dto.FollowDto;
import com.vifrin.common.dto.ProfileDto;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import com.vifrin.user.service.UserService;
import com.vifrin.common.dto.UserDto;
import com.vifrin.common.payload.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<ResponseTemplate<UserDto>> createUser(@RequestBody RegisterRequest registerRequest) {
        log.info("Inside createUser of UserController");
        UserDto userDto = userService.createUser(registerRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/" + userDto.getId())
                .buildAndExpand(userDto.getUsername()).toUri();
        return ResponseEntity
                .created(location)
                .body(new ResponseTemplate<UserDto>(ResponseType.CREATED, userDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        ResponseTemplate<UserDto> responseTemplate = new ResponseTemplate<UserDto>(ResponseType.OK, userDto);
        return ResponseEntity.ok().body(responseTemplate);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal Principal principal) {
        UserDto userDto = userService.getUserByUsername(principal.getName());
        ResponseTemplate<UserDto> responseTemplate = new ResponseTemplate<UserDto>(ResponseType.OK, userDto);
        return ResponseEntity.ok().body(responseTemplate);
    }

    @GetMapping
    public ResponseEntity<?> getUserByUsernameOrId(@RequestParam Optional<String> username,
                                                   @RequestParam Optional<Long> id ){
        UserDto userDto = null;
        if (username.isPresent()){
            userDto = userService.getUserByUsername(username.get());
        } else if (id.isPresent()){
            userDto = userService.getUserById(id.get());
        }
        return userDto != null ?
                ResponseEntity.ok(new ResponseTemplate<UserDto>(ResponseType.OK, userDto)) :
                ResponseEntity.badRequest().body(new ResponseTemplate<>(ResponseType.CANNOT_GET, null));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal Principal principal) {
        ProfileDto profileDto = userService.getProfile(principal.getName());
        return ResponseEntity.ok(new ResponseTemplate<ProfileDto>(ResponseType.OK, profileDto));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileDto profileDto, @AuthenticationPrincipal Principal principal) {
        userService.updateProfile(profileDto, principal.getName());
        return ResponseEntity.ok(new ResponseTemplate<ProfileDto>(ResponseType.OK, null));
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> updateAvatar(@RequestBody ProfileDto body, @AuthenticationPrincipal Principal principal) {
        userService.updateAvatar(body, principal.getName());
        return ResponseEntity.ok(new ResponseTemplate(ResponseType.OK, null));
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<?> follow(@PathVariable Long id, @AuthenticationPrincipal Principal principal) {
        return userService.follow(id, principal.getName()) ?
                ResponseEntity.ok(new ResponseTemplate<>(ResponseType.OK, null)) :
                ResponseEntity.badRequest().body(new ResponseTemplate<>(ResponseType.CANNOT_FOLLOW, null));
    }

    @DeleteMapping("/follow/{id}")
    public ResponseEntity<?> removeFollow(@PathVariable Long id, @AuthenticationPrincipal Principal principal) {
        return userService.removeFollow(id, principal.getName()) ?
                ResponseEntity.ok(new ResponseTemplate<>(ResponseType.OK, null)) :
                ResponseEntity.badRequest().body(new ResponseTemplate<>(ResponseType.CANNOT_REMOVE_FOLLOW, null));
    }

    @DeleteMapping("/unfollow/{id}")
    public ResponseEntity<?> unfollow(@PathVariable Long id, @AuthenticationPrincipal Principal principal) {
        return userService.unfollow(id, principal.getName()) ?
                ResponseEntity.ok(new ResponseTemplate<>(ResponseType.OK, null)) :
                ResponseEntity.badRequest().body(new ResponseTemplate<>(ResponseType.CANNOT_UNFOLLOW, null));
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<FollowDto>> getFollowers(@PathVariable Long userId, @AuthenticationPrincipal Principal principal,
                                          @RequestParam(value = "page", defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
                                          @RequestParam(value = "size", defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size) {
        List<FollowDto> followDtos = userService.getFollowers(userId, principal.getName(), page, size);
        return !followDtos.isEmpty() ?
                ResponseEntity.ok(followDtos) :
                ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/followings")
    public ResponseEntity<List<FollowDto>> getFollowings(@PathVariable Long userId, @AuthenticationPrincipal Principal principal,
                                           @RequestParam(value = "page", defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
                                           @RequestParam(value = "size", defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size) {
        List<FollowDto> followDtos = userService.getFollowings(userId, principal.getName(), page, size);
        return !followDtos.isEmpty() ?
                ResponseEntity.ok(followDtos) :
                ResponseEntity.noContent().build();
    }
}

