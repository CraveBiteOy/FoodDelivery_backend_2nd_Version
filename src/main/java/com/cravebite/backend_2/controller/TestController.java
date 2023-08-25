package com.cravebite.backend_2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cravebite.backend_2.dtos.RegisterDto;
import com.cravebite.backend_2.models.User;
import com.cravebite.backend_2.service.CustomUserDetailService;
import com.cravebite.backend_2.service.TestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(testService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTestUsers() {
        testService.addTestUsers();
        return new ResponseEntity<>("Test users added", HttpStatus.CREATED);
    }

    @PostMapping("/dto")
    public ResponseEntity<String> testDto(@Valid @RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>("DTO received: " + registerDto.toString(), HttpStatus.OK);
    }

    @GetMapping("/load-user/{username}")
    public ResponseEntity<UserDetails> loadUserByUsername(@PathVariable String username) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

}
