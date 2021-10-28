package com.snw.auth.controller;

import com.snw.auth.payload.AuthenticationResponse;
import com.snw.auth.service.AuthService;
import com.vifrin.common.payload.CreateUserResponse;
import com.vifrin.common.payload.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Arrays.stream;

/**
 * @Author: trantuananh1
 * @Created: Sun, 12/09/2021 4:39 PM
 **/

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    @Autowired AuthService authService;

    @PostMapping("/register")
    public CreateUserResponse register(@RequestBody RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }


//    @PostMapping("/login")
//    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
//        return authService.login(loginRequest);
//    }

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

    @PostMapping("/logout")
    public AuthenticationResponse logout(){
        return new AuthenticationResponse();
    }


}
