package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.LoginDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class LoginController {
    private final LoginDAO loginDAO;

    public LoginController(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    @PostMapping
    public String logIn() {
        return "Home";
    }
}
