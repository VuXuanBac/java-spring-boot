package com.vuxuanbac.security.securitybeginner.service;

import com.vuxuanbac.security.securitybeginner.entity.JwtToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public interface JwtTokenService {
    JwtToken save(String token, LocalDateTime expiredDate, String username);
    void deleteByUsername(String username);
    boolean isValidToken(String token);
    void deleteByToken(String token);
}
