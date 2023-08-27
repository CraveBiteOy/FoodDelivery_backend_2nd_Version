package com.cravebite.backend_2.service;

import com.cravebite.backend_2.dtos.LoginDto;
import com.cravebite.backend_2.dtos.RegisterDto;
import com.cravebite.backend_2.models.User;

public interface AuthService {
    User registerUser(RegisterDto registerDto);

    String loginUser(LoginDto loginDto);

}
