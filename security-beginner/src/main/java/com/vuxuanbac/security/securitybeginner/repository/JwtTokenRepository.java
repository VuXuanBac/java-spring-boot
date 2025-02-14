package com.vuxuanbac.security.securitybeginner.repository;

import com.vuxuanbac.security.securitybeginner.entity.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByToken(String token);
    void deleteByUsername(String username);

    void deleteByToken(String username);
}
