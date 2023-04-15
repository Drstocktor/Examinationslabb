package com.example.examinationslabb.controller;


import com.example.examinationslabb.model.Book;
import com.example.examinationslabb.model.Game;
import com.example.examinationslabb.model.Movie;
import com.example.examinationslabb.service.WebShopService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private WebShopService webShopService;

    @GetMapping("/admin")
    public String admin(Model model) {
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


    @GetMapping("/admin/add-products")
    public String addProducts() {
        return "add-product";
    }

    @GetMapping("/admin/add-movie")
    public String getAddMovie(Model model) {
        model.addAttribute(new Movie());
        return "add-movie";
    }

    @PostMapping("/admin/add-movie")
    public String addMovie(@Valid Movie movie, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("inputFormMessage", "Invalid input");
        } else {
            webShopService.addMovie(movie);
            model.addAttribute("inputFormMessage", "Product added");
        }
        return "add-movie";
    }

    @GetMapping("/admin/add-book")
    public String getAddBook(Model model) {
        model.addAttribute(new Book());
        return "add-book";
    }

    @PostMapping("/admin/add-book")
    public String addBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("inputFormMessage", "Invalid input");
        } else {
            webShopService.addBook(book);
            model.addAttribute("inputFormMessage", "Product added");
        }
        return "add-book";
    }

    @GetMapping("/admin/add-game")
    public String getAddGame(Model model) {
        model.addAttribute(new Game());
        return "add-game";
    }

    @PostMapping("/admin/add-game")
    public String addGame(@Valid Game game, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("inputFormMessage", "Invalid input");
        } else {
            webShopService.addGame(game);
            model.addAttribute("inputFormMessage", "Product added");
        }
        return "add-game";
    }


}
