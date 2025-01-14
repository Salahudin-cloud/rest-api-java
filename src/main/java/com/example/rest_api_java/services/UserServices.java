package com.example.rest_api_java.services;

import com.example.rest_api_java.entity.User;
import com.example.rest_api_java.model.UserRegisterRequest;
import com.example.rest_api_java.model.UserResponse;

public interface UserServices {

    public void register(UserRegisterRequest userRegisterRequest);

    public UserResponse get(User user);

}
