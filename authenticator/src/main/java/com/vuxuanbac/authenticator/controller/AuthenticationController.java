package com.vuxuanbac.authenticator.controller;

import com.vuxuanbac.authenticator.dto.AccountRegistrationDTO;
import com.vuxuanbac.authenticator.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class AuthenticationController {
    private IAccountService service;

    @Autowired
    public AuthenticationController(IAccountService service) {
        this.service = service;
    }

//    @ModelAttribute("account")
//    public AccountRegistrationDTO getAccountRegistrationDTO(){
//        return new AccountRegistrationDTO();
//    }

    @GetMapping("admin")
    @ResponseBody
    public String getAdmin(){
        return "Login success to ADMIN";
    }
    @GetMapping("user")
    @ResponseBody
    public String getUser(){
        return "Login success to USER";
    }
    @GetMapping("manager")
    @ResponseBody
    public String getManager(){
        return "Login success to MANAGER";
    }
    @GetMapping("login")
    public String login(){
        return "login";
    }
    @GetMapping("login/example")
    @ResponseBody
    public String loginWithExample(){
        return "Login with Example";
    }

    @GetMapping("hello")
    public String register2(Model model){
        model.addAttribute("account", new AccountRegistrationDTO());
        System.out.println(model);
        return "registration";
    }
    @GetMapping("register")
    public String register(Model model){
        model.addAttribute("account", new AccountRegistrationDTO());
        System.out.println(model);
        return "registration";
    }

    @PostMapping("register")
    public String register(@ModelAttribute("account") AccountRegistrationDTO account_dto){
        System.out.println(account_dto);
        service.save(account_dto);
        return "redirect:/register?success";
    }
}
