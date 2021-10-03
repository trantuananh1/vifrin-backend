package com.snw.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snw.auth.payload.AuthenticationResponse;
import com.snw.auth.payload.LoginRequest;
import com.snw.auth.util.JwtProvider;
import com.vifrin.common.entity.Role;
import com.vifrin.common.entity.User;
import com.vifrin.common.repository.*;
import com.vifrin.feign.client.UserFeignClient;
import com.vifrin.common.payload.CreateUserResponse;
import com.vifrin.common.payload.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

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
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired UserRepository userRepository;

    public CreateUserResponse register(RegisterRequest registerRequest){
        log.info("registering user " + registerRequest.getUsername());
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        CreateUserResponse user = userFeignClient.createUser(registerRequest);
//        Authentication authenticate = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(registerRequest.getUsername(), registerRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authenticate);
//        String token = jwtProvider.generateToken(authenticate);
        return user;
    }

    public AuthenticationResponse login(LoginRequest loginRequest){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userFeignClient.findByUserName(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null) {
            try {
                String refresh_token = authorization.startsWith("Bearer ")?authorization.substring("Bearer ".length()):authorization;
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userRepository.findByUsername(username)
                        .orElseThrow(()->new RuntimeException("Username not exist"));
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> errors = new HashMap<>();
                errors.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
