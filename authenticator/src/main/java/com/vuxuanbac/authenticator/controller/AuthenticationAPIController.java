package com.vuxuanbac.authenticator.controller;

import com.vuxuanbac.authenticator.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationAPIController {

    @Autowired
    AuthenticationProvider authenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO info){
        System.out.println(info.getUsername() + " " + info.getPassword());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(info.getUsername(), info.getPassword());
        authenticationProvider.authenticate(token);
        return ResponseEntity.ok("login successfully");
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(){
        return null;
    }
}
