package com.snw.user.service;

import com.snw.user.VO.Department;
import com.snw.user.VO.ResponseTemplateVO;
import com.snw.user.entity.Profile;
import com.snw.user.entity.User;
import com.snw.user.payload.UpdateProfileRequest;
import com.snw.user.repository.UserRepository;
import com.vifrin.common.type.ResponseType;
import com.vifrin.feign.client.UserFeignClient;
import com.vifrin.feign.payload.CreateUserResponse;
import com.vifrin.feign.payload.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public CreateUserResponse createUser(RegisterRequest registerRequest) {
        log.info("Inside saveUser of UserService");
        User user = new User(registerRequest.getUsername(), registerRequest.getPassword());

        Profile profile = new Profile(registerRequest.getEmail(), registerRequest.getFullName());
        user.setProfile(profile);

        userRepository.save(user);

        return CreateUserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername()).build();
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
