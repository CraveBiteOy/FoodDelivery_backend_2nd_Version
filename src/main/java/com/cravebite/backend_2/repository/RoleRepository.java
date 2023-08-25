package com.cravebite.backend_2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cravebite.backend_2.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
