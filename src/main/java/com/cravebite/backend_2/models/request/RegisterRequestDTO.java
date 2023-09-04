package com.cravebite.backend_2.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "username cannot be blank")
    private String username;

    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotBlank(message = "email cannot be blank")
    private String firstname;

    @NotBlank(message = "email cannot be blank")
    private String surename;

    @NotBlank(message = "email cannot be blank")
    private double longitude;

    @NotBlank(message = "email cannot be blank")
    private double latitude;
}
