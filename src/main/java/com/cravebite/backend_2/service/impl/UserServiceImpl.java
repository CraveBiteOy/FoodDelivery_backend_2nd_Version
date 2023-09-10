package com.cravebite.backend_2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.models.entities.User;
import com.cravebite.backend_2.models.mappers.UserMapper;
import com.cravebite.backend_2.repository.UserRepository;
import com.cravebite.backend_2.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    // get user by id
    @Override
    public User getUserbyId(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();

    }

    // get user by username
    @Override
    public User getUserbyUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    // get authenticated user
    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return getUserbyUsername(username);
    }

}
