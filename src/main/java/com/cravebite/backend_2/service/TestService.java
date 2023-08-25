package com.cravebite.backend_2.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cravebite.backend_2.models.Role;
import com.cravebite.backend_2.models.User;
import com.cravebite.backend_2.repository.RoleRepository;
import com.cravebite.backend_2.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class TestService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        if (roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setName("ROLE_ADMIN");
            roleRepository.save(admin);

            Role user = new Role();
            user.setName("ROLE_USER");
            roleRepository.save(user);
        }
    }

    public void addTestUsers() {

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword("123456");
        user1.setRoles(Set.of(adminRole));

        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword("123456");
        user2.setRoles(Set.of(userRole));

        userRepository.save(user1);
        userRepository.save(user2);

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
