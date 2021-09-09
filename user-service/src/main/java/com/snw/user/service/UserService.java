package com.snw.user.service;

import com.snw.user.VO.Department;
import com.snw.user.VO.ResponseTemplateVO;
import com.snw.user.entity.User;
import com.snw.user.repository.UserRepository;
import com.vifrin.common.type.ResponseType;
import com.vifrin.feign.client.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("Inside saveUser of UserService");
        return userRepository.save(user);
        UserFeignClient
    }

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
