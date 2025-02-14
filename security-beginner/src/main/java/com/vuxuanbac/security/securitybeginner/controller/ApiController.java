package com.vuxuanbac.security.securitybeginner.controller;

import com.vuxuanbac.security.securitybeginner.dto.LoginRequest;
import com.vuxuanbac.security.securitybeginner.dto.LoginResponse;
import com.vuxuanbac.security.securitybeginner.dto.MessageResponse;
import com.vuxuanbac.security.securitybeginner.dto.RegisterRequest;
import com.vuxuanbac.security.securitybeginner.entity.User;
import com.vuxuanbac.security.securitybeginner.security.CustomUserDetails;
import com.vuxuanbac.security.securitybeginner.security.JwtUtils;
import com.vuxuanbac.security.securitybeginner.service.JwtTokenService;
import com.vuxuanbac.security.securitybeginner.service.RoleService;
import com.vuxuanbac.security.securitybeginner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest info){
        if (userService.existUsername(info.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userService.existEmail(info.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }
        User user = userService.save(info, roleService);

        System.out.println(user);
        return ResponseEntity.ok(user.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest info){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                info.getUsername(),
                info.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Setup Token
        Date expiredDate = Date.from(LocalDateTime.now().plusSeconds(JwtUtils.EXPIRATION_SECONDS)
                .atZone(ZoneId.systemDefault()).toInstant());
        String token = jwtUtils.generateJwtToken(authentication, expiredDate);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        // Save to Database
        jwtTokenService.save(token,
                LocalDateTime.ofInstant(expiredDate.toInstant(), ZoneId.systemDefault()),
                info.getUsername());
        return ResponseEntity.ok(new LoginResponse(token, expiredDate, roles));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = AUTHORIZATION) String token){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("** Logout: Authentication " + authentication.toString());
        jwtTokenService.deleteByToken(token.split(" ")[1]);
        return ResponseEntity.ok(new MessageResponse("Logout successfully: " + authentication.getPrincipal()));
    }


    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Instead of setting AntMatchers, but must @EnableGlobalMethodSecurity(prePostEnabled = true) in Configuration Class
    public ResponseEntity<?> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return ResponseEntity.ok(new MessageResponse("Hello World"));
    }

    @GetMapping("/data")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<?> authenticatedResource(){
        return ResponseEntity.ok(new MessageResponse("The content can be accessed by Authenticated User"));
    }
}
