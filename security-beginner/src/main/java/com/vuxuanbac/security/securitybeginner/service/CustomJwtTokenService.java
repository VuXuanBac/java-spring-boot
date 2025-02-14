package com.vuxuanbac.security.securitybeginner.service;

import com.vuxuanbac.security.securitybeginner.entity.JwtToken;
import com.vuxuanbac.security.securitybeginner.repository.JwtTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class CustomJwtTokenService implements JwtTokenService {
    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Override
    public JwtToken save(String token, LocalDateTime expiredDate, String username) {
        JwtToken jwtToken = new JwtToken(token, expiredDate, username);
        return jwtTokenRepository.save(jwtToken);
    }
    @Override
    @Transactional
    public void deleteByUsername(String username) {
        jwtTokenRepository.deleteByUsername(username);
    }

    @Override
    public boolean isValidToken(String tokenStr) {
        var record = jwtTokenRepository.findByToken(tokenStr);
        if (record.isPresent()) {
            JwtToken token = record.get();
            boolean isExpired = token.getExpiredDate().isBefore(LocalDateTime.now());
            if (isExpired)
                jwtTokenRepository.deleteById(token.getId());
            return !isExpired;
        }
        return false;
    }

    @Override
    @Transactional
    public void deleteByToken(String token) {
        jwtTokenRepository.deleteByToken(token);
    }
}
