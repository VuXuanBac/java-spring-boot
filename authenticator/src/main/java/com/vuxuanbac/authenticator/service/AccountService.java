package com.vuxuanbac.authenticator.service;

import com.vuxuanbac.authenticator.dto.AccountRegistrationDTO;
import com.vuxuanbac.authenticator.model.Account;
import com.vuxuanbac.authenticator.model.Role;
import com.vuxuanbac.authenticator.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService{
    private AccountRepository repository;

    @Autowired
    public AccountService(AccountRepository repository){
        this.repository = repository;
    }
    @Override
    public Account save(AccountRegistrationDTO account_dto) {
        Account acc = AccountRegistrationDTO.extract(account_dto);
        acc.setRoles(List.of(new Role("USER")));

        return repository.save(acc);
    }
}
