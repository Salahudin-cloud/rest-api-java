package com.example.rest_api_java.controller;

import com.example.rest_api_java.entity.User;
import com.example.rest_api_java.model.TokenResponse;
import com.example.rest_api_java.model.UserLoginRequest;
import com.example.rest_api_java.model.WebResponse;
import com.example.rest_api_java.repository.UserRepository;
import com.example.rest_api_java.security.BCrypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void loginFailedNotFound() throws Exception {
        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("test");
        request.setPassword("test");

        mockMvc.perform(
                post("/api/v1/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            String response = result.getResponse().getContentAsString();

            assertNotNull(response);
        });
    }

    @Test
    void loginWrongPassword() throws Exception {
        User user = new User();
        user.setName("user_test");
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        userRepository.save(user);

        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("test");
        request.setPassword("asdf");

        mockMvc.perform(
                post("/api/v1/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            String response = result.getResponse().getContentAsString();
            assertNotNull(response);
        });
    }

    @Test
    void loginSuccess() throws Exception {
        User user = new User();
        user.setName("user_test");
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
        userRepository.save(user);

        UserLoginRequest request = new UserLoginRequest();
        request.setUsername("test");
        request.setPassword("test");

        mockMvc.perform(
                post("/api/v1/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            TokenResponse response = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    TokenResponse.class
            );

            assertNotNull(response.getToken());
            assertNotNull(response.getExpiredAt());

            // Retrieve user from the database to verify token and expiration date
            Optional<User> db = userRepository.findByUsername(request.getUsername());

            assertTrue(db.isPresent(), "User should be present in the database");

            User userDB = db.get();

            assertNotNull(db);
            assertEquals(userDB.getToken(), response.getToken());
            assertEquals(userDB.getTokenExpiredAt(), response.getExpiredAt());

        });
    }


}