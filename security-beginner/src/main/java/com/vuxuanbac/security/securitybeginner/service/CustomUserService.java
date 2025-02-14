package com.vuxuanbac.security.securitybeginner.service;
import com.vuxuanbac.security.securitybeginner.dto.RegisterRequest;
import com.vuxuanbac.security.securitybeginner.entity.Role;
import com.vuxuanbac.security.securitybeginner.entity.User;
import com.vuxuanbac.security.securitybeginner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserService implements UserService{
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public User save(RegisterRequest account_dto, RoleService roleService) {
        account_dto.setPassword(encoder.encode(account_dto.getPassword()));
        User acc = RegisterRequest.extract(account_dto, roleService);

        return repository.save(acc);
    }

    @Override
    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Error: UserID " + id + " is not found."));
    }

    @Override
    public boolean existUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existEmail(String email) {
        return repository.existsByEmail(email);
    }
}
