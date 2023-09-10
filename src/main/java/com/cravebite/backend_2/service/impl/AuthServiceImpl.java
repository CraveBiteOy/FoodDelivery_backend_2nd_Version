package com.cravebite.backend_2.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.exception.APIException;
import com.cravebite.backend_2.models.entities.Role;
import com.cravebite.backend_2.models.entities.User;
import com.cravebite.backend_2.models.request.LoginRequestDTO;
import com.cravebite.backend_2.models.request.RegisterRequestDTO;
import com.cravebite.backend_2.models.response.LoginResponse;
import com.cravebite.backend_2.models.response.SignupResponse;
import com.cravebite.backend_2.repository.RoleRepository;
import com.cravebite.backend_2.repository.UserRepository;
import com.cravebite.backend_2.service.AuthService;
import com.cravebite.backend_2.service.CustomUserDetailService;
import com.cravebite.backend_2.utils.JWTUtils;

import jakarta.annotation.PostConstruct;

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

    @PostConstruct
    public void init() {
        if (roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setName("ROLE_ADMIN");
            roleRepository.save(admin);

            Role user = new Role();
            user.setName("ROLE_USER");
            roleRepository.save(user);
        }
    }

    @Override
    public SignupResponse registerUser(RegisterRequestDTO registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new APIException(HttpStatus.CONFLICT, "A user with this username already exists");
        }

        User newUser = new User();
        newUser.setUsername(registerDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Assign default role to new user
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Role is not found."));
        newUser.setRoles(Set.of(userRole));

        userRepository.save(newUser);

        return SignupResponse.builder()
                .message("User registered successfully")
                .build();
    }

    @Override
    public LoginResponse loginUser(LoginRequestDTO loginDto) {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        UserDetails userDetails = customUserDetailService.loadUserByUsername(loginDto.getUsername());

        return LoginResponse.builder()
                .accessToken(jwtUtils.generateToken(userDetails))
                .build();
    }

}