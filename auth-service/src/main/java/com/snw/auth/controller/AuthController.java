package com.snw.auth.controller;

import com.snw.auth.payload.AuthenticationResponse;
import com.snw.auth.payload.LoginRequest;
import com.snw.auth.service.AuthService;
import com.vifrin.feign.payload.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: trantuananh1
 * @Created: Sun, 12/09/2021 4:39 PM
 **/

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AuthenticationResponse register(RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/logout")
    public AuthenticationResponse logout(){
        return new AuthenticationResponse();
    }
}
