package com.vifrin.user.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vifrin.common.response.ResponseConstant;
import com.vifrin.user.exception.EmailAlreadyExistsException;
import com.vifrin.user.exception.UsernameAlreadyExistsException;
import com.vifrin.user.exception.UsernameNotExistsException;
import com.vifrin.user.mapper.UserMapper;
import com.vifrin.common.entity.Profile;
import com.vifrin.common.entity.User;
import com.vifrin.common.payload.UserDto;
import com.vifrin.common.repository.*;
import com.vifrin.common.payload.request.RegisterRequest;
import com.vifrin.feign.client.AuthFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    AuthFeignClient authFeignClient;
    @Autowired
    UserMapper userMapper;

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

    public UserDto getUserByUserName(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(UsernameNotExistsException::new);
        return userMapper.userToUserDto(user);
    }

//    public GetUserResponse getUserByUserId(long userId) {
//        User user = userRepository.findByUserId(userId)
//                .orElseThrow(() -> new RuntimeException("UserId not exist"));
//        return GetUserResponse.builder()
//                .userId(user.getUserId())
//                .username(user.getUsername())
//                .isEnabled(user.isEnabled())
//                .createdAt(user.getCreatedAt())
//                .updatedAt(user.getUpdatedAt())
//                .roles(user.getRoles())
//                .build();
//    }

//    public void addRoleToUser(String username, String roleName) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("Username not exist"));
//        Role role = roleRepository.findByName(roleName)
//                .orElseThrow(() -> new RuntimeException("Role not exist"));
//        user.getRoles().add(role);
//        userRepository.save(user);
//    }

    @Transactional(readOnly = true)
    public UserDto getCurrentUser(String token) {
        UserDto userna = authFeignClient.getCurrentUser().getBody();
        token = token.startsWith("Bearer ") ? token.substring("Bearer ".length()) : token;
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("Username not exist"));
//        UserDto userDto = authFeignClient.getCurrentUser(token).getBody();
        return userMapper.userToUserDto(user);
    }

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
