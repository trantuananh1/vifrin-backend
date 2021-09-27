package com.snw.auth.service;

import com.snw.auth.payload.AuthenticationResponse;
import com.snw.auth.payload.LoginRequest;
import com.snw.auth.util.JwtProvider;
import com.vifrin.feign.client.UserFeignClient;
import com.vifrin.feign.payload.CreateUserResponse;
import com.vifrin.feign.payload.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: trantuananh1
 * @Created: Sun, 12/09/2021 4:42 PM
 **/

@Service
@Slf4j
@Transactional
public class AuthService {
    @Autowired UserFeignClient userFeignClient;
    @Autowired AuthenticationManager authenticationManager;
    @Autowired JwtProvider jwtProvider;

    public AuthenticationResponse register(RegisterRequest registerRequest){
        log.info("registering user " + registerRequest.getUsername());
        CreateUserResponse user = userFeignClient.createUser(registerRequest);
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerRequest.getUsername(), registerRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token, user.getUsername());
    }

    public AuthenticationResponse login(LoginRequest loginRequest){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}
