package com.cravebite.backend_2.service;

import java.util.List;

import com.cravebite.backend_2.models.entities.User;

public interface UserService {

    User getUserbyId(Long id);

    List<User> getAllUsers();

    User getUserbyUsername(String username);

}
