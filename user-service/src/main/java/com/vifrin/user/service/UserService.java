package com.vifrin.user.service;

import com.vifrin.common.dto.FollowDto;
import com.vifrin.common.dto.ProfileDto;
import com.vifrin.common.dto.UserSummary;
import com.vifrin.user.exception.EmailAlreadyExistsException;
import com.vifrin.user.exception.ResourceNotFoundException;
import com.vifrin.user.exception.UsernameAlreadyExistsException;
import com.vifrin.user.mapper.ProfileMapper;
import com.vifrin.user.mapper.UserMapper;
import com.vifrin.common.entity.Profile;
import com.vifrin.common.entity.User;
import com.vifrin.common.dto.UserDto;
import com.vifrin.common.repository.*;
import com.vifrin.common.payload.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

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
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
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
    public UserDto getUserByUsername(String username, String myName) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        User me = userRepository.findByUsername(myName).get();
        return userMapper.userToUserDto(user, me);
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return userMapper.userToUserDto(user);
    }

    public List<User> findAllByIds(List<Long> ids){
        return userRepository.findAllById(ids);
    }

    public UserSummary getUserSummary(Long userId, String username){
        User me = userRepository.findByUsername(username).get();
        return userRepository.findById(userId)
                .map(user -> userMapper.userToUserSummary(user, me))
                .orElseThrow(() -> new ResourceNotFoundException(userId));
    }

    public List<UserSummary> getUserSummaries(List<Long> ids, String username){
        User me = userRepository.findByUsername(username).get();
        return userRepository.findAllById(ids).stream()
                .map(user -> userMapper.userToUserSummary(user, me))
                .collect(Collectors.toList());
    }

    public ProfileDto getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        Profile profile = profileRepository.getOne(user.getId());
        return profileMapper.profileToProfileDto(profile);
    }

    public void updateProfile(ProfileDto profileDto, String username) {
        Profile profile = profileMapper.profileDtoToProfile(profileDto);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        profile.setUser(user);
        profile.setUserId(user.getId());
        profileRepository.save(profile);

        user.setUpdatedAt(Instant.now());
        userRepository.save(user);
    }

    public void updateAvatar(ProfileDto body, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        user.setAvatarUrl(body.getAvatarUrl());
        userRepository.save(user);
    }

    public boolean follow(Long targetId, String username) {
        try {
            User target = userRepository.findById(targetId)
                    .orElseThrow(() -> new ResourceNotFoundException(targetId));
            User me = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException(username));

            target.getFollowers().add(me);
            me.getFollowings().add(target);

            target.getActivity().setFollowersCount(target.getFollowers().size());
            me.getActivity().setFollowingsCount(me.getFollowings().size());

            userRepository.save(target);
            userRepository.save(me);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean removeFollow(Long targetId, String username) {
        try {
            User target = userRepository.findById(targetId)
                    .orElseThrow(() -> new ResourceNotFoundException(targetId));
            User me = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException(username));

            if (!me.getFollowers().contains(target)) {
                return false;
            }

            target.getFollowings().remove(me);
            me.getFollowers().remove(target);

            target.getActivity().setFollowingsCount(target.getFollowings().size());
            me.getActivity().setFollowersCount(me.getFollowers().size());

            userRepository.save(target);
            userRepository.save(me);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean unfollow(Long targetId, String username) {
        try {
            User target = userRepository.findById(targetId)
                    .orElseThrow(() -> new ResourceNotFoundException(targetId));
            User me = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException(username));

            if (!me.getFollowings().contains(target)){
                return false;
            }

            target.getFollowers().remove(me);
            me.getFollowings().remove(target);

            target.getActivity().setFollowersCount(target.getFollowers().size());
            me.getActivity().setFollowingsCount(me.getFollowings().size());

            userRepository.save(target);
            userRepository.save(me);
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    public List<FollowDto> getFollowers(Long targetId, String username, int page, int size) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        List<Long> followerIds = userRepository.getFollowersByUserId(targetId, PageRequest.of(page, size));
        List<User> followers = userRepository.findAllById(followerIds);
        return userMapper.userListToFollowDtoList(List.copyOf(followers), user);
    }

    public List<FollowDto> getFollowings(Long targetId, String username, int page, int size) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        List<Long> followingIds = userRepository.getFollowingsByUserId(targetId, PageRequest.of(page, size));
        List<User> followings = userRepository.findAllById(followingIds);
        return userMapper.userListToFollowDtoList(followings, user);
    }
}
