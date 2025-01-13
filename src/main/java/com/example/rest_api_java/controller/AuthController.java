package com.example.rest_api_java.controller;


import com.example.rest_api_java.model.TokenResponse;
import com.example.rest_api_java.model.UserLoginRequest;
import com.example.rest_api_java.model.WebResponse;
import com.example.rest_api_java.repository.UserRepository;
import com.example.rest_api_java.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private AuthServices authServices;

    @PostMapping(
            path = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public TokenResponse login (@RequestBody UserLoginRequest request)  {
       return authServices.Login(request);
    }

}
