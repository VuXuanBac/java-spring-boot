package com.vuxuanbac.security.securitybeginner.security;

import com.vuxuanbac.security.securitybeginner.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("dbUserDetailsService")
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("** Jwt Token Filter: do Filter");
        // JWT Token in Request Header appear as a field with key: Authorization
        // and value: "Bearer <token>"
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer")){
            // if Request Header does not a identify a valid JWT Token -> continue filter
            filterChain.doFilter(request, response);
            return;
        }

        // get the token
        String token = authorizationHeader.split(" ")[1].trim();
        if(jwtTokenService.isValidToken(token) && jwtUtils.isValidToken(token)) {
            System.out.println("** Jwt Token Filter: Valid Token");
            // With this username and authorities, determined which resources this user can access
            String username = jwtUtils.getUserNameFromJwtToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // With this username and authorities, determined which resources this user can access
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("** Jwt Token Filter: Athentication " + authentication.toString());
        }
        filterChain.doFilter(request, response);
    }
}