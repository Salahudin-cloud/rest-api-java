package com.example.rest_api_java.controller;


import com.example.rest_api_java.model.UserRegisterRequest;
import com.example.rest_api_java.model.WebResponse;
import com.example.rest_api_java.repository.UserRepository;
import com.example.rest_api_java.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;

    @PostMapping(
            path = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register (@RequestBody UserRegisterRequest request) {
        userServices.register(request);
        return WebResponse.<String>builder().message("OK").build();
    }

}
