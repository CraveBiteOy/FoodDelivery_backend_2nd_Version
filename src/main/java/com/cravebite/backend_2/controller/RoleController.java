package com.cravebite.backend_2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cravebite.backend_2.models.Role;
import com.cravebite.backend_2.service.RoleService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("load-role/{username}")
    public ResponseEntity<Role> getRolebyUsername(@PathVariable String username) {
        return new ResponseEntity<>(roleService.getRolebyUsername(username), HttpStatus.OK);
    }

}
