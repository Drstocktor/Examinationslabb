package com.example.examinationslabb.service;

import com.example.examinationslabb.model.*;
import com.example.examinationslabb.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WebShopServiceTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    private  GameRepository gameRepository;
    @Mock
    private  MovieRepository movieRepository;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  OrderRepository orderRepository;
    @Mock
    private WebShopService webShopService;
    @BeforeEach
    void setUp() {
        webShopService = new WebShopService(bookRepository, gameRepository, movieRepository, userRepository, orderRepository);
    }


    @Test
    void findProduct() {
        Mockito.when(bookRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new Book("Dune", "Frank Herbert", 50)));

        assertInstanceOf(Product.class, webShopService.findProduct(1L, "Book"));

    }

    @Test
    void addProductToCart() {
        Mockito.when(bookRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new Book("Dune", "Frank Herbert", 50)));


        webShopService.addProductToCart(1L, "Book", 1);

        assertEquals(1, webShopService.getShoppingCartList().size());
        Mockito.verify(bookRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void removeProductFromCart() {
        Mockito.when(bookRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new Book("Dune", "Frank Herbert", 50)));

        webShopService.addProductToCart(1L, "Book", 5);
        webShopService.removeProductFromCart(1L, "Book", 1);

        assertEquals(4, webShopService.getShoppingCartList().size());
    }
    @Test
    void removeAllOfCertainProductFromCart() {
        Mockito.when(bookRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new Book("Dune", "Frank Herbert", 50)));

        webShopService.addProductToCart(1L, "Book", 5);
        webShopService.removeAllOfCertainProductFromCart(1L,"Book");
        assertEquals(0, webShopService.getShoppingCartList().size());
    }
    @Test
    void placeOrder() {
        Mockito.when(bookRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new Book("Dune", "Frank Herbert", 50)));

        Mockito.when(movieRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new Movie("Dune", "Frank Herbert", 2000, 60)));

        Mockito.when(userRepository.findByUsername("name"))
                .thenReturn(Optional.of(new User("Victor", "victor", UserType.USER)));

        webShopService.addProductToCart(1L, "Book", 5);
        webShopService.addProductToCart(1L, "Movie", 3);

        assertEquals(8, webShopService.getShoppingCartList().size());

        Map<Product, Integer> testMap = webShopService.placeOrder("name");

        assertEquals(2, testMap.size());
        assertEquals(0, webShopService.getShoppingCartList().size());

    }
}