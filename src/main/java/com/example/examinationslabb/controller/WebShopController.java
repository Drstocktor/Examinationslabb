package com.example.examinationslabb.controller;

import com.example.examinationslabb.service.UserService;
import com.example.examinationslabb.service.WebShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

        // TODO: 2023-04-12 add user here already in webservice?
        return "home";
    }

    @GetMapping("/products")
    public String products(@RequestParam(name = "product", required = false) String category,
                           @RequestParam(name = "search", required = false) String search,
                           Model model) {
        if (search == null) {
            model.addAttribute("productChoice", webShopService.getProductByType(category));
        } else {
            model.addAttribute("productChoice", webShopService.searchProducts(search));
        }
        return "view-products";
    }

    @GetMapping("/product-details")
    public String productDetails(@RequestParam(name = "productId") Long id,
                                 @RequestParam(name = "productCategory") String category, Model model) {
        switch (category) {
            case "Book" -> model.addAttribute("product", webShopService.findBookById(id));
            case "Movie" -> model.addAttribute("product", webShopService.findMovieById(id));
            case "Game" -> model.addAttribute("product", webShopService.findGameById(id));
            default -> {
            }
        }
        return "product";
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("shoppingCart", webShopService.getShoppingCart());
        return "cart";
    }
    @PostMapping("/cart")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam String productCategory,
                            Model model,
                            Authentication authentication) {
        webShopService.addProductToCart(productId, productCategory, authentication.getName());
        model.addAttribute("shoppingCart", webShopService.getShoppingCart());
        return "cart";

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
