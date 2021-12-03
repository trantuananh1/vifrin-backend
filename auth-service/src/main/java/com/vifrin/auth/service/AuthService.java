package com.vifrin.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vifrin.auth.exception.UsernameAlreadyExistsException;
import com.vifrin.auth.validator.EmailValidator;
import com.vifrin.common.entity.ConfirmationToken;
import com.vifrin.common.payload.NotificationEmail;
import com.vifrin.common.payload.UserDto;
import com.vifrin.common.entity.User;
import com.vifrin.common.repository.*;
import com.vifrin.common.response.ResponseConstant;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import com.vifrin.feign.client.UserFeignClient;
import com.vifrin.common.payload.request.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.util.Arrays.stream;

/**
 * @Author: trantuananh1
 * @Created: Sun, 12/09/2021 4:42 PM
 **/

@Service
@Slf4j
@Transactional
public class AuthService {
    @Autowired
    UserFeignClient userFeignClient;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailValidator emailValidator;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    MailService mailService;

    public UserDto register(RegisterRequest registerRequest) throws UsernameAlreadyExistsException, IOException {
        log.info("registering user " + registerRequest.getUsername());
        boolean isValidEmail = emailValidator.test(registerRequest.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("");
        }
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        ResponseTemplate<UserDto> responseTemplate = userFeignClient.createUser(registerRequest).getBody();
        UserDto user = responseTemplate.getData();
        if (user == null){
            throw new IllegalStateException("can't create user");
        }
        //send verify email
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, Instant.now(), Instant.now().plus(15, ChronoUnit.MINUTES), userRepository.findByUserId(user.getUserId()).get());
        confirmationTokenRepository.save(confirmationToken);
        mailService.sendMail(new NotificationEmail("Activate your Account",
                user.getEmail(), "Thank you for signing up to Vifrin with username '" + user.getUsername() + "', " +
                "please click on the below url to activate your account : "  + confirmationToken.getToken()));
        return user;
    }

//    public AuthenticationResponse login(LoginRequest loginRequest){
//        Authentication authenticate = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authenticate);
//        String token = jwtProvider.generateToken(authenticate);
//        return new AuthenticationResponse(token, loginRequest.getUsername());
//    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null) {
            try {
                String refresh_token = authorization.startsWith("Bearer ") ? authorization.substring("Bearer ".length()) : authorization;
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("Username not exist"));
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRole())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refresh_token);
                ResponseTemplate responseTemplate = new ResponseTemplate(ResponseType.OK, tokens);
                response.setContentType(MediaType.APPLICATION_JSON);
                new ObjectMapper().writeValue(response.getOutputStream(), responseTemplate);

            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> errors = new HashMap<>();
                errors.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        }
    }

    @Transactional
    public boolean verify(String token){
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(()->new IllegalStateException("token not found"));
        if (confirmationToken.getConfirmedAt()!=null){
            throw new IllegalStateException("email already confirmed");
        }
        Instant expiredAt = confirmationToken.getExpiredAt();
        if (expiredAt.isBefore(Instant.now())){
            throw new IllegalStateException("token expired");
        }
        confirmationToken.setConfirmedAt(Instant.now());
        confirmationTokenRepository.save(confirmationToken);
        enableUser(confirmationToken.getUser());
        return true;
    }

    public void enableUser(User user){
        user.setEnabled(true);
        userRepository.save(user);
    }
}