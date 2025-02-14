package com.vuxuanbac.security.securitybeginner.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private Date expiredDate;

    private List<String> roles;
}
