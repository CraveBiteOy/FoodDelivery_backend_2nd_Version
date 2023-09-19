package com.cravebite.backend_2.models.response;

import java.util.List;

import com.cravebite.backend_2.models.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String firstname;
    private String surename;
    private List<Role> roles;
    // private Double longitude;
    // private Double latitude;

}
