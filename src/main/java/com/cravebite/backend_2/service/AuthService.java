package com.cravebite.backend_2.service;

import com.cravebite.backend_2.dtos.LoginDto;
import com.cravebite.backend_2.dtos.RegisterDto;
import com.cravebite.backend_2.models.response.LoginResponse;
import com.cravebite.backend_2.models.response.SignupResponse;

public interface AuthService {
    SignupResponse registerUser(RegisterDto registerDto);

    LoginResponse loginUser(LoginDto loginDto);

}
