package com.vuxuanbac.authenticator.dto;

import com.vuxuanbac.authenticator.model.Account;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountRegistrationDTO {
    private String email;
    private String username;
    private String password;

    public static Account extract(AccountRegistrationDTO dto){
        Account acc = new Account();
        acc.setEmail(dto.getEmail());
        acc.setUsername(dto.getUsername());
        acc.setPassword(dto.getPassword());
        return acc;
    }
}
