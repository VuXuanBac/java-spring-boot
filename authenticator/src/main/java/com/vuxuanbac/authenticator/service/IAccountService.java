package com.vuxuanbac.authenticator.service;

import com.vuxuanbac.authenticator.dto.AccountRegistrationDTO;
import com.vuxuanbac.authenticator.model.Account;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {
    Account save(AccountRegistrationDTO account);
}
