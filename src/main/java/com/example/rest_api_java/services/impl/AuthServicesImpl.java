package com.example.rest_api_java.services.impl;

import com.example.rest_api_java.entity.User;
import com.example.rest_api_java.model.TokenResponse;
import com.example.rest_api_java.model.UserLoginRequest;
import com.example.rest_api_java.repository.UserRepository;
import com.example.rest_api_java.security.BCrypt;
import com.example.rest_api_java.services.AuthServices;
import com.example.rest_api_java.services.ValidatorServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@Service
public class AuthServicesImpl implements AuthServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidatorServices validatorServices;

    @Transactional
    @Override
    public TokenResponse Login(UserLoginRequest request) {
        validatorServices.Validate(request);

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong"));

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong");
        }
        user.setToken(UUID.randomUUID().toString());
        user.setTokenExpiredAt(tokenLimitTime());

        userRepository.save(user);

        return TokenResponse.builder()
                .token(user.getToken())
                .expiredAt(user.getTokenExpiredAt())
                .build();
    }


    private Long tokenLimitTime() {
        return System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30 );
    }
}
