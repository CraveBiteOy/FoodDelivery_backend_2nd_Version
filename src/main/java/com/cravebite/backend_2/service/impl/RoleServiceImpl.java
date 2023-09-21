package com.cravebite.backend_2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.exception.CraveBiteGlobalExceptionHandler;
import com.cravebite.backend_2.models.entities.Role;
import com.cravebite.backend_2.repository.RoleRepository;
import com.cravebite.backend_2.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role getRolebyUsername(String username) {
        return roleRepository.findByName(username)
                .orElseThrow(() -> new CraveBiteGlobalExceptionHandler(HttpStatus.NOT_FOUND, " Role is not found."));
    }

}
