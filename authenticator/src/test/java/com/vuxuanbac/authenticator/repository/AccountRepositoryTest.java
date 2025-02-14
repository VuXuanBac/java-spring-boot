package com.vuxuanbac.authenticator.repository;

import com.vuxuanbac.authenticator.model.Account;
import com.vuxuanbac.authenticator.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {
    @Autowired
    private AccountRepository repository;

    //@Test
    public void saveAccount(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        Role user = new Role("USER");
        Role admin = new Role("ADMIN");
        Role manager = new Role("MANAGER");
        Account acc1 = new Account("user@gmail.com", "user", encoder.encode("123"), user);
        Account acc2 = new Account("admin@gmail.com", "admin", encoder.encode("123"), admin);
        Account acc3 = new Account("manager@gmail.com", "manager", encoder.encode("123"), manager);
        repository.saveAll(List.of(acc1, acc2, acc3));
    }
}