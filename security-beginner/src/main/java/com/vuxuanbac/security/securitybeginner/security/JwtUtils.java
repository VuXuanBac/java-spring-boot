package com.vuxuanbac.security.securitybeginner.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtUtils {

    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private String SECRET_KEY = "unique_key";

    public static int EXPIRATION_SECONDS = 86400;
    public String generateJwtToken(Authentication authentication, Date expiredDate) {
        System.out.println("** Generate Jwt Token");
        CustomUserDetails accountDetails = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(accountDetails.getUsername())
                //.setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .claim("roles", accountDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isValidToken(String token) {
        System.out.println("** Validate Jwt Token");
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
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

