package com.vuxuanbac.authenticator.configuration;

import com.vuxuanbac.authenticator.model.AccountDetails;
import com.vuxuanbac.authenticator.service.DbUserDetailsService;
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

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("dbUserDetailService")
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // JWT Token in Request Header appear as a field with key: Authorization
        // and value: "Bearer <token>"
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer")){
            // if Request Header does not a identify a valid JWT Token -> continue filter
            filterChain.doFilter(request, response);
            return;
        }

        // get the token
        String token = authorizationHeader.split(" ")[1].trim();
        if(!jwtUtils.validateJwtToken(token)){
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtils.getUserNameFromJwtToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
