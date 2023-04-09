package com.example.examinationslabb.controller;

import com.example.examinationslabb.service.UserService;
import com.example.examinationslabb.service.WebShopService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebShopController {
    private final UserService userService;
    private final WebShopService webShopService;

    public WebShopController(UserService userService, WebShopService webShopService) {
        this.userService = userService;
        this.webShopService = webShopService;
    }

    @GetMapping("/home")
    public String home(Model m) {
        return "home";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add_product")
    public String addProduct(Model m) {
        return "add_product";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/add")
    public String add() {
        return "add_product";
    }
}
