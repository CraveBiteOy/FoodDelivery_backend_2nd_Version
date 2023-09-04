package com.cravebite.backend_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cravebite.backend_2.models.request.LoginRequestDTO;
import com.cravebite.backend_2.models.request.RegisterRequestDTO;
import com.cravebite.backend_2.models.response.LoginResponse;
import com.cravebite.backend_2.models.response.SignupResponse;
import com.cravebite.backend_2.service.AuthService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequestDTO loginResquestDTO) throws Exception {
        return ResponseEntity.ok(authService.loginUser(loginResquestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<SignupResponse> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO)
            throws Exception {
        return new ResponseEntity<>(authService.registerUser(registerRequestDTO), HttpStatus.CREATED);
    }

}
