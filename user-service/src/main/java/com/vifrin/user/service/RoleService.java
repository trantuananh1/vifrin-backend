package com.vifrin.user.service;

import com.vifrin.common.entity.Role;
import com.vifrin.common.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role createRole(Role role){
        return roleRepository.save(role);
    }
}
