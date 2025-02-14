package com.vuxuanbac.security.securitybeginner.security;

import com.vuxuanbac.security.securitybeginner.entity.User;
import com.vuxuanbac.security.securitybeginner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("dbUserDetailsService")
public class DbUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    // Override this method to indicate where and how to get
    // Username-Password (For Authentication) and Authorities (For Authorization)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User acc = repository.findByUsername(username);
        if(acc == null)
            throw new UsernameNotFoundException("Username " + username + " not found!");
        return new CustomUserDetails(acc);
    }
}
