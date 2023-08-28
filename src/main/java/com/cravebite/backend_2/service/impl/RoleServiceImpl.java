package com.cravebite.backend_2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.models.Role;
import com.cravebite.backend_2.repository.RoleRepository;
import com.cravebite.backend_2.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role getRolebyUsername(String username) {
        return roleRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }

}
