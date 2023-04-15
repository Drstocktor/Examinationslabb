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

    @GetMapping("/cart")
    public String getCart(Model model) {
        // todo fix shopping cart
        model.addAttribute("shoppingCartList", webShopService.getShoppingCartList());
        model.addAttribute("totalPrice", webShopService.getTotalPrice());
        model.addAttribute("shoppingCart", webShopService.getShoppingCart());
        return "cart";
    }

    @PostMapping("/remove-product")
    public String removeFromCart(@RequestParam Long productId,
                                 @RequestParam String productCategory,
                                 @RequestParam int quantity,
                                 Model model) {

        // TODO: 2023-04-14 finish implementation of remove from cart

        webShopService.removeProductFromCart(productId, productCategory, quantity);
        model.addAttribute("shoppingCartList", webShopService.getShoppingCartList());
        model.addAttribute("totalPrice", webShopService.getTotalPrice());
        model.addAttribute("shoppingCart", webShopService.getShoppingCart());
        return "cart";
    }

    @PostMapping("/cart")
    public String addToCart(@RequestParam Long productId,
                            @RequestParam String productCategory,
                            @RequestParam int quantity,
                            Model model) {
        webShopService.addProductToCart(productId, productCategory, quantity);
        model.addAttribute("shoppingCartList", webShopService.getShoppingCartList());
        model.addAttribute("totalPrice", webShopService.getTotalPrice());
        model.addAttribute("shoppingCart", webShopService.getShoppingCart());
        return "cart";

    }

    @PostMapping("/orders")
    public String orders(Model model,
                         Authentication authentication) {

        // TODO: 2023-04-14 SHOULD SHOW QUANTITY OF items instead of listing same item
        model.addAttribute("totalPrice", webShopService.getTotalPrice());
        model.addAttribute("productsOrdered", webShopService.placeOrder(authentication.getName()));
        return "order";
    }

    @GetMapping("/orders")
    public String getOrders() {
        return "redirect:/products";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        // TODO: 2023-04-14 finish admin page with form to add new products
        model.addAttribute("unpaidOrders", webShopService.getUnpaidOrders());
        model.addAttribute("paidOrders", webShopService.getPaidOrders());
        return "admin-page";
    }

    @PostMapping("/admin")
    public String postAdmin(@RequestParam Long orderId, Model model) {
        webShopService.chargeCustomer(orderId);
        model.addAttribute("unpaidOrders", webShopService.getUnpaidOrders());
        model.addAttribute("paidOrders", webShopService.getPaidOrders());
        return "admin-page";
    }

//    @PreAuthorize("hasAuthority('Admin')")
//    @GetMapping("/test")
//    public String test() {
//        return "test";
//    }
}
