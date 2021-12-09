package com.vifrin.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vifrin.common.dto.FollowDto;
import com.vifrin.common.dto.ProfileDto;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import com.vifrin.user.exception.EmailAlreadyExistsException;
import com.vifrin.user.exception.ResourceNotFoundException;
import com.vifrin.user.exception.UsernameAlreadyExistsException;
import com.vifrin.user.exception.UsernameNotExistsException;
import com.vifrin.user.mapper.ProfileMapper;
import com.vifrin.user.mapper.UserMapper;
import com.vifrin.common.entity.Profile;
import com.vifrin.common.entity.User;
import com.vifrin.common.payload.UserDto;
import com.vifrin.common.repository.*;
import com.vifrin.common.payload.request.RegisterRequest;
import com.vifrin.feign.client.AuthFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProfileMapper profileMapper;

    public UserDto createUser(RegisterRequest registerRequest) {
        log.info("Inside saveUser of UserService");
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new UsernameAlreadyExistsException();
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException();
        }
        User user = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
        userRepository.save(user);

        Profile profile = new Profile(registerRequest.getEmail(), registerRequest.getFullName());
        profile.setUser(user);
        profileRepository.save(profile);

        user.setProfile(profile);
        userRepository.save(user);

        return userMapper.userToUserDto(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        return userMapper.userToUserDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return userMapper.userToUserDto(user);
    }

    public ProfileDto getProfile(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        Profile profile = profileRepository.getOne(user.getId());
        return profileMapper.profileToProfileDto(profile);
    }

    public void updateProfile(ProfileDto profileDto, String username){
        Profile profile = profileMapper.profileDtoToProfile(profileDto);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        profile.setUser(user);
        profile.setUserId(user.getId());
        profileRepository.save(profile);

        user.setUpdatedAt(Instant.now());
        userRepository.save(user);
    }

    public void updateAvatar(ProfileDto body, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        user.setAvatarUrl(body.getAvatarUrl());
        userRepository.save(user);
    }

    public void follow(Long targetId, String username){
        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException(targetId));
        User follower = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));

        target.getFollowers().add(follower);
        follower.getFollowings().add(target);

        target.getActivity().setFollowersCount(target.getFollowers().size());
        follower.getActivity().setFollowingsCount(follower.getFollowings().size());

        userRepository.save(target);
        userRepository.save(follower);
    }

    public void unfollow(Long targetId, String username){
        User target = userRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException(targetId));
        User follower = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));

        target.getFollowers().remove(follower);
        follower.getFollowings().remove(target);

        target.getActivity().setFollowersCount(target.getFollowers().size());
        follower.getActivity().setFollowingsCount(follower.getFollowings().size());

        userRepository.save(target);
        userRepository.save(follower);
    }

    public List<FollowDto> getFollowers(Long targetId, String username, int page, int size){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        List<Long> followerIds = userRepository.getFollowersByUserId(targetId, PageRequest.of(page, size));
        List<User> followers = userRepository.findAllById(followerIds);
        return userMapper.userListToFollowDtoList(List.copyOf(followers), user);
    }

    public List<FollowDto> getFollowings(Long targetId, String username, int page, int size){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        List<Long> followerIds = userRepository.getFollowingsByUserId(targetId, PageRequest.of(page, size));
        List<User> followers = userRepository.findAllById(followerIds);
        return userMapper.userListToFollowDtoList(followers, user);
    }
}
