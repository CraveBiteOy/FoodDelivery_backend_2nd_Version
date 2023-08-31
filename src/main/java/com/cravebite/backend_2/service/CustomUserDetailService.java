package com.cravebite.backend_2.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cravebite.backend_2.models.entities.User;
import com.cravebite.backend_2.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Find the user by username and set granted authorities to the user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        // Create a UserDetails object from the user object and return it
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                grantedAuthorities);

    }

}

/**
 * we test it now to use it once we get into jwt imple
 * NOTE:
 * will serve as the bridge between your application's user
 * database and Spring Security's authentication and authorization mechanisms.
 * 
 * It tells Spring Security:
 * - Who the user is (via username)
 * - If they are who they say they are (via password)
 * - What they are allowed to do (via roles/authorities)
 */
