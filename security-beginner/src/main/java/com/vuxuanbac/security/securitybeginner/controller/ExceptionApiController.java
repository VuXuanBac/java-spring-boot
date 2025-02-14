package com.vuxuanbac.security.securitybeginner.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

// Exceptions from ApiController will be mapped to this Controller
@RestControllerAdvice(assignableTypes = {ApiController.class})
public class ExceptionApiController {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> notFoundHandler(NoSuchElementException ex, HttpServletRequest request){
        String[] splits = request.getRequestURI().split("/");
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "No " +  splits[2] + " found with id " + splits[3]);
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> validateFailHandler(BindException ex){
        StringBuilder message = new StringBuilder();
        if (ex.getBindingResult().hasErrors())
            for (ObjectError err : ex.getBindingResult().getAllErrors()) {
                message.append(err.getDefaultMessage()).append("\n");
            }
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error", "Invalid Data");
        body.put("message", message.toString());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> loginFailHandler(BadCredentialsException ex){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error", "Login Fail");
        body.put("message", "Username or Password is not correct");
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<?> nullPointerHandler(AccessDeniedException ex, HttpServletRequest request){
//
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("message", ex.getMessage());
//        body.put("path", request.getRequestURI());
//        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
//    }
}