package com.vuxuanbac.security.securitybeginner.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "The username can not be empty")
    private String username;
    @Size(min = 6, message = "The password must be at least 6 characters")
    private String password;

}