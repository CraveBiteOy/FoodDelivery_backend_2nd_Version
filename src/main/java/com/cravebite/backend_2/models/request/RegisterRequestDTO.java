package com.cravebite.backend_2.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "firstname cannot be blank")
    private String firstname;

    @NotBlank(message = "surename cannot be blank")
    private String surename;

    @NotNull(message = "longitude cannot be null")
    private double longitude;

    @NotNull(message = "latitude cannot be null")
    private double latitude;
}
