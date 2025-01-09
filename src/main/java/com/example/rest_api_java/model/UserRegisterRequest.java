package com.example.rest_api_java.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequest {
    @NotBlank
    @Size(max = 100)
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max = 100)
    private String name;

}
