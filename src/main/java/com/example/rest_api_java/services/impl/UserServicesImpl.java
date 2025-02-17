package com.example.rest_api_java.services.impl;

import com.example.rest_api_java.entity.User;
import com.example.rest_api_java.model.UserRegisterRequest;
import com.example.rest_api_java.model.UserResponse;
import com.example.rest_api_java.repository.UserRepository;
import com.example.rest_api_java.security.BCrypt;
import com.example.rest_api_java.services.UserServices;
import com.example.rest_api_java.services.ValidatorServices;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Set;


@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidatorServices validatorServices;



    @Override
    @Transactional
    public void register(UserRegisterRequest userRegisterRequest) {

        validatorServices.Validate(userRegisterRequest);

        if (userRepository.existsByUsername(userRegisterRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already registered");
        }

        User user = new User();
        user.setUsername(userRegisterRequest.getUsername());
        user.setPassword(BCrypt.hashpw(userRegisterRequest.getPassword(), BCrypt.gensalt()));
        user.setName(userRegisterRequest.getName());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(null);

        userRepository.save(user);

    }

    @Override
    public UserResponse get(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }
}
