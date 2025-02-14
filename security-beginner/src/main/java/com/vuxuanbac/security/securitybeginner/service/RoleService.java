package com.vuxuanbac.security.securitybeginner.service;

import com.vuxuanbac.security.securitybeginner.entity.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role getRoleByName(String role);
}
