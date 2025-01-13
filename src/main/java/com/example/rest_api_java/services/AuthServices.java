package com.example.rest_api_java.services;

import com.example.rest_api_java.model.TokenResponse;
import com.example.rest_api_java.model.UserLoginRequest;

public interface AuthServices {


    public TokenResponse Login(UserLoginRequest request);


}
