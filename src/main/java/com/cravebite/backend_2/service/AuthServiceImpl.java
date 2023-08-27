package com.cravebite.backend_2.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.dtos.LoginDto;
import com.cravebite.backend_2.dtos.RegisterDto;
import com.cravebite.backend_2.models.Role;
import com.cravebite.backend_2.models.User;
import com.cravebite.backend_2.repository.RoleRepository;
import com.cravebite.backend_2.repository.UserRepository;
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
    private CustomUserDetailService customUserDetailService;

    @Override
    public User registerUser(RegisterDto registerDto) {
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

        return userRepository.save(newUser);
    }

    @Override
    public String loginUser(LoginDto loginDto) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(loginDto.getUsername());
        if (!passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtils.generateToken(userDetails);
    }

}