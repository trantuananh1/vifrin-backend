package com.vifrin.auth.controller;

import com.vifrin.auth.exception.UsernameAlreadyExistsException;
import com.vifrin.common.dto.UserDto;
import com.vifrin.common.payload.AuthenticationResponse;
import com.vifrin.auth.service.AuthService;
import com.vifrin.common.payload.RegisterRequest;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
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
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) throws UsernameAlreadyExistsException, IOException {
        UserDto userDto = authService.register(registerRequest);
        return ResponseEntity.ok(new ResponseTemplate(ResponseType.CREATED, userDto));
    }

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }

    @PutMapping("/password")
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