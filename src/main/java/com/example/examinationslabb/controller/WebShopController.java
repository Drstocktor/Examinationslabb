package com.example.examinationslabb.controller;

import com.example.examinationslabb.service.UserService;
import com.example.examinationslabb.service.WebShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
        if (product != null) {
            if (product.equals("books")) {
                model.addAttribute("productChoice", webShopService.getAllBooks());
            } else if (product.equals("movies")) {
                model.addAttribute("productChoice", webShopService.getAllMovies());
            } else if (product.equals("games")) {
                model.addAttribute("productChoice", webShopService.getAllGames());
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
