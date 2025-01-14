package com.example.rest_api_java.controller;

import com.example.rest_api_java.entity.User;
import com.example.rest_api_java.model.UserRegisterRequest;
import com.example.rest_api_java.model.WebResponse;
import com.example.rest_api_java.repository.UserRepository;
import com.example.rest_api_java.security.BCrypt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        assertEquals(0, userRepository.count(), "Database not wiped clean");
    }

    @Test
    void testRegisterSuccess() throws  Exception{
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("test");
        request.setPassword("asdf");
        request.setName("udin");

        mockMvc.perform(
            post("/api/v1/users")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new
                    TypeReference<WebResponse<String>>() {
                    });

            assertEquals("OK", response.getMessage());
        });
    }

    @Test
    void testRegisterBadRequest() throws  Exception{
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("");
        request.setPassword("asdf");
        request.setName("udin");

        mockMvc.perform(
                post("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new
                    TypeReference<WebResponse<String>>() {
                    });

            assertNotNull(response.getMessage());
        });
    }


    @Test
    void testRegisterWhenUserExist() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("asdf", BCrypt.gensalt()));
        user.setName("udin");
        user.setCreatedAt(new Date());
        userRepository.save(user);

        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("test");
        request.setPassword("asdf");
        request.setName("udin");

        mockMvc.perform(
                post("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new
                    TypeReference<WebResponse<String>>() {
                    });

            assertNotNull(response.getMessage());
        });
    }


    @Test
    void getUserUnauthorized() throws Exception {
        mockMvc.perform(
                get("/api/v1/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-TOKEn-API", "notfound")
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new
                    TypeReference<WebResponse<String>>() {
                    });

            assertNotNull(response.getMessage());
        });
    }

    @Test
    void getUserUnauthorizedTokenSend() throws Exception {
        mockMvc.perform(
                get("/api/v1/users/current")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new
                    TypeReference<WebResponse<String>>() {
                    });

            assertNotNull(response.getMessage());
        });
    }

    @Test
    void getUserSuccess() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("asdf", BCrypt.gensalt()));
        user.setName("user_test");
        user.setToken("user_testing");
        user.setTokenExpiredAt(System.currentTimeMillis() - 10000000);
        user.setCreatedAt(new Date());
        userRepository.save(user);

        mockMvc.perform(
                get("/api/v1/users/current")
                        .accept(MediaType.APPLICATION_JSON)
                        .header("X-TOKEn-API", "user_testing")
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new
                    TypeReference<WebResponse<String>>() {
                    });

            assertNotNull(response.getMessage());
        });
    }

}