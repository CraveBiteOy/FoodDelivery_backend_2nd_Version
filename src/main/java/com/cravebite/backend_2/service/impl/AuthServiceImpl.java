package com.cravebite.backend_2.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.dtos.LoginDto;
import com.cravebite.backend_2.dtos.RegisterDto;
import com.cravebite.backend_2.models.Role;
import com.cravebite.backend_2.models.User;
import com.cravebite.backend_2.models.response.LoginResponse;
import com.cravebite.backend_2.models.response.SignupResponse;
import com.cravebite.backend_2.repository.RoleRepository;
import com.cravebite.backend_2.repository.UserRepository;
import com.cravebite.backend_2.service.AuthService;
import com.cravebite.backend_2.service.CustomUserDetailService;
import com.cravebite.backend_2.utils.JWTUtils;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    public SignupResponse registerUser(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User newUser = new User();
        newUser.setUsername(registerDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Assign default role to new user
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        newUser.setRoles(Set.of(userRole));

        userRepository.save(newUser);

        return SignupResponse.builder()
                .message("User registered successfully")
                .build();
    }

    // @Override
    // public LoginResponse loginUser(LoginDto loginDto) {
    // UserDetails userDetails =
    // customUserDetailService.loadUserByUsername(loginDto.getUsername());
    // if (!passwordEncoder.matches(loginDto.getPassword(),
    // userDetails.getPassword())) {
    // throw new RuntimeException("Invalid credentials");
    // }
    // return jwtUtils.generateToken(userDetails);
    // return LoginResponse.builder()
    // .accessToken(jwtUtils.generateToken(userDetails))
    // .build();
    // }

    @Override
    public LoginResponse loginUser(LoginDto loginDto) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        UserDetails userDetails = customUserDetailService.loadUserByUsername(loginDto.getUsername());

        return LoginResponse.builder()
                .accessToken(jwtUtils.generateToken(userDetails))
                .build();
    }
}