package com.cravebite.backend_2.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cravebite.backend_2.models.entities.User;
import com.cravebite.backend_2.models.mappers.UserMapper;
import com.cravebite.backend_2.models.response.UserResponseDTO;
import com.cravebite.backend_2.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("auth")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    // get all users
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> userResponseDTOs = users.stream()
                .map(userMapper::toUserResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(userResponseDTOs, HttpStatus.OK);
    }

    // get user by id
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserbyId(id), HttpStatus.OK);
    }

    // get user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(userService.getUserbyUsername(username), HttpStatus.OK);
    }

    // get authenticated user
    @GetMapping("/me")
    public ResponseEntity<User> getAuthenticatedUser() {
        return new ResponseEntity<>(userService.getAuthenticatedUser(), HttpStatus.OK);
    }

}
