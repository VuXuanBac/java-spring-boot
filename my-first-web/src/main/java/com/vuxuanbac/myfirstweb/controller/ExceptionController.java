package com.vuxuanbac.myfirstweb.controller;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Exceptions from all other controllers will be mapped to this Controller
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> nullPointerHandler(Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
