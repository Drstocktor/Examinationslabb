package com.example.examinationslabb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost() {
        return "redirect:/home";
    }

    @GetMapping("/user")
    public String user() {
        return "Hello User";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin";
    }
}
