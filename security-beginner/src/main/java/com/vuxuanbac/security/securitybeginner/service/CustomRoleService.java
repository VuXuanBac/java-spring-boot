package com.vuxuanbac.security.securitybeginner.service;

import com.vuxuanbac.security.securitybeginner.entity.Role;
import com.vuxuanbac.security.securitybeginner.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomRoleService implements RoleService{
    @Autowired
    private RoleRepository repository;

    @Override
    public Role getRoleByName(String role) {
        return repository.findByRoleName(role).orElseThrow(() -> new RuntimeException("Error: Role " + role + " is not found."));
    }
}
