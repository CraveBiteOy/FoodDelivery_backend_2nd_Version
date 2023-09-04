package com.cravebite.backend_2.service;

import com.cravebite.backend_2.models.request.LoginRequestDTO;
import com.cravebite.backend_2.models.request.RegisterRequestDTO;
import com.cravebite.backend_2.models.response.LoginResponse;
import com.cravebite.backend_2.models.response.SignupResponse;

public interface AuthService {
    SignupResponse registerUser(RegisterRequestDTO registerDto);

    LoginResponse loginUser(LoginRequestDTO loginDto);

}
