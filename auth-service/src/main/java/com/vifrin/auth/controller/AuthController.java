package com.vifrin.auth.controller;

import com.vifrin.common.payload.response.AuthenticationResponse;
import com.vifrin.auth.service.AuthService;
import com.vifrin.common.payload.request.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

    @PostMapping("/password")
    public void changePassword() throws IOException {
//        authService.refreshToken();
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String token) {
//        authService.verify(token);
        return ResponseEntity.ok("verified");
    }

    @PostMapping("/logout")
    public AuthenticationResponse logout() {
        return new AuthenticationResponse();
    }
}