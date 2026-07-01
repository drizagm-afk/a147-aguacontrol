package com.example.aguacontrol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInController {
    @GetMapping("/login")
    public String logIn() {
        return "auth/login";
    }

    @GetMapping("/home")
    public String home() {
        return "surface/home";
    }

    @GetMapping("/business/home")
    public String businessHome() {
        return "business/home";
    }
}
