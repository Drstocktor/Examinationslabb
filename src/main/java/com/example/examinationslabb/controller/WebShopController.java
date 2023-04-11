package com.example.examinationslabb.controller;

import com.example.examinationslabb.service.UserService;
import com.example.examinationslabb.service.WebShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebShopController {
    private final UserService userService;
    private final WebShopService webShopService;

    @Autowired
    public WebShopController(UserService userService, WebShopService webShopService) {
        this.userService = userService;
        this.webShopService = webShopService;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/products")
    public String products(@RequestParam(name = "product", required = false) String product, Model model) {
        String choice = "productChoice";
        if (product != null) {
            switch (product) {
                case "books" -> model.addAttribute(choice, webShopService.getAllBooks());
                case "movies" -> model.addAttribute(choice, webShopService.getAllMovies());
                case "games" -> model.addAttribute(choice, webShopService.getAllGames());
                default -> {
                    break;
                }
            }
        }
        return "products";
    }

    @GetMapping("/add_product")
    public String addProduct(Model m) {
        return "add_product";
    }

    @GetMapping("/add")
    public String add() {
        return "add_product";
    }

//    @PreAuthorize("hasRole('ADMIN')")
}
