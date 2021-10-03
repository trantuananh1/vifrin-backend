package com.snw.user.service;

import com.vifrin.common.entity.Profile;
import com.vifrin.common.entity.Role;
import com.vifrin.common.entity.User;
import com.vifrin.common.payload.GetUserResponse;
import com.vifrin.common.repository.*;
import com.vifrin.common.payload.CreateUserResponse;
import com.vifrin.common.payload.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private RoleRepository roleRepository;

    public CreateUserResponse createUser(RegisterRequest registerRequest) {
        log.info("Inside saveUser of UserService");
        User user = new User(registerRequest.getUsername(), registerRequest.getPassword());
        userRepository.save(user);

        Profile profile = new Profile(registerRequest.getEmail(), registerRequest.getFullName());
        profile.setUser(user);
        profileRepository.save(profile);

        user.setProfile(profile);
        userRepository.save(user);

        return CreateUserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername()).build();
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public GetUserResponse getUserByUserName(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(()-> new RuntimeException("Username not exist"));
        return GetUserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .isEnabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .roles(user.getRoles())
                .build();
    }

    public GetUserResponse getUserByUserId(long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(()-> new RuntimeException("UserId not exist"));
        return GetUserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .isEnabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .roles(user.getRoles())
                .build();
    }

    public void addRoleToUser(String username, String roleName){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("Username not exist"));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(()-> new RuntimeException("Role not exist"));
        user.getRoles().add(role);
        userRepository.save(user);
    }

//    public GetUserResponse getUserByUserId(String userId) {
//        Optional<User> userIdOptional = userRepository.findByUserId(userId);
//        User userById = userIdOptional.orElseThrow(() ->
//                new RunTimeExceptionPlaceHolder("UserName or Email doesn't exist!!")
//        );
//
//        return GetUserResponse.builder()
//                .userId(userById.getUserId())
//                .userName(userById.getUserName())
//                .firstName(userById.getFirstName())
//                .lastName(userById.getLastName())
//                .email(userById.getEmail())
//                .roles(userById.getRoles())
//                .build();
//    }
//    public boolean updateProfile(UpdateProfileRequest request){
//
//    }
//    public ResponseTemplateVO getUserWithDepartment(Long userId) {
//        log.info("Inside getUserWithDepartment of UserService");
//        ResponseTemplateVO vo = new ResponseTemplateVO();
//        User user = userRepository.findByUserId(userId);
//
//        Department department =
//                restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/" + user.getDepartmentId()
//                        , Department.class);
//
//        vo.setUser(user);
//        vo.setDepartment(department);
//
//        return vo;
//    }
}
