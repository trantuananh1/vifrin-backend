package com.snw.auth.service;

import com.vifrin.common.entity.User;
import com.vifrin.common.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

/**
 * @Author: trantuananh1
 * @Created: Tue, 21/09/2021 10:40 AM
 **/

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("No user " + "found with username : " + username));
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        user.getRole().forEach(role->{
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        });
        return new org.springframework.security
                .core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}
