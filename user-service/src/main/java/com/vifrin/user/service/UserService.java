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
import com.vifrin.user.messaging.UserEventSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    @Autowired
    UserEventSender userEventSender;

    public UserDto createUser(RegisterRequest registerRequest) {
        log.info("Inside saveUser of UserService");
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        User user = new User(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        user.setAvatarUrl(registerRequest.getAvatarUrl());
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

            userEventSender.sendEventFollow(me.getId(), targetId);
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
        User user = username.isEmpty() ? null :
                userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        List<Long> followerIds = userRepository.getFollowersByUserId(targetId, PageRequest.of(page, size));
        List<User> followers = userRepository.findAllById(followerIds);
        return userMapper.userListToFollowDtoList(List.copyOf(followers), user);
    }

    public List<FollowDto> getFollowings(Long targetId, String username, int page, int size) {
        User user = username.isEmpty() ? null :
                userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        List<Long> followingIds = userRepository.getFollowingsByUserId(targetId, PageRequest.of(page, size));
        List<User> followings = userRepository.findAllById(followingIds);
        return userMapper.userListToFollowDtoList(followings, user);
    }

    public List<UserSummary> getFollowSuggestions(String username, int size){
        User user = userRepository.findByUsername(username).get();
        Set<User> suggestions = new HashSet<>();
        //get following from people you are following
        Set<User> followings = user.getFollowings();
        for (User following : followings){
            for (User tmpUser : following.getFollowings()){
                if (suggestions.size() == size){
                    break;
                }
                if (user!=tmpUser && !user.getFollowings().contains(tmpUser)){
                    suggestions.add(tmpUser);
                }
            }
        }
        //get strange user
        List<User> strangers = userRepository.findAll();
        for (int i = 0; i<strangers.size(); i++){
            User tmpUser = strangers.get(i);
            if (suggestions.size() == size){
                break;
            }
            if (user!=tmpUser && !user.getFollowings().contains(tmpUser)){
                suggestions.add(tmpUser);
            }
        }
        return userMapper.usersToUserSummaries(List.copyOf(suggestions), user);
    }

    public List<UserSummary> searchUser(String key, String username){
        User user = userRepository.findByUsername(username).get();
        List<User> matchedUsers = userRepository.findByUsernameContainingIgnoreCase(key);
        matchedUsers.addAll(userRepository.findByFullNameContainingIgnoreCase(key));
        matchedUsers.remove(user);
        matchedUsers = matchedUsers.stream().distinct().collect(Collectors.toList());
        return userMapper.usersToUserSummaries(matchedUsers, user);
    }
}
