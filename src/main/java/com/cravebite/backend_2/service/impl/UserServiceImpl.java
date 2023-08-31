package com.cravebite.backend_2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.models.entities.User;
import com.cravebite.backend_2.repository.UserRepository;
import com.cravebite.backend_2.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserbyId(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserbyUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Error: User is not found."));
    }

}
