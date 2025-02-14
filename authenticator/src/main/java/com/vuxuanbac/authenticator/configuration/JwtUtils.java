package com.vuxuanbac.authenticator.configuration;

import com.vuxuanbac.authenticator.model.AccountDetails;
import com.vuxuanbac.authenticator.service.DbUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtils {

    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private String SECRET_KEY = "unique_key";
    private int EXPIRATION = 86400000;

    public String generateJwtToken(Authentication authentication) {

        AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((accountDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
