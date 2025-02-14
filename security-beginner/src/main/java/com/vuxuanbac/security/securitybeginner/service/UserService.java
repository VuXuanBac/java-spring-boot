package com.vuxuanbac.security.securitybeginner.service;

import com.vuxuanbac.security.securitybeginner.dto.RegisterRequest;
import com.vuxuanbac.security.securitybeginner.entity.Role;
import com.vuxuanbac.security.securitybeginner.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User save(RegisterRequest info, RoleService roleService);

    User getUserById(Long id);

    boolean existUsername(String username);

    boolean existEmail(String email);
}
