package com.vuxuanbac.authenticator.service;

import com.vuxuanbac.authenticator.model.Account;
import com.vuxuanbac.authenticator.model.AccountDetails;
import com.vuxuanbac.authenticator.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("dbUserDetailsService")
public class DbUserDetailsService implements UserDetailsService {
    private AccountRepository repository;
    @Autowired
    public DbUserDetailsService(AccountRepository repository) {
        this.repository = repository;
    }

    // Override this method to indicate where and how to get Username-Password
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account acc = repository.findByUsername(username);
        System.out.println(acc.getRoles());
        return new AccountDetails(acc);
    }
}
