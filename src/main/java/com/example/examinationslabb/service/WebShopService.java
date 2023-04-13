package com.example.examinationslabb.service;

import com.example.examinationslabb.model.*;
import com.example.examinationslabb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@SessionScope
public class WebShopService {
    private final BookRepository bookRepository;
    private final GameRepository gameRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private List<Product> shoppingCart = new ArrayList<>();

    private Product currentProduct;
    private User user = new User();

    @Autowired
    public WebShopService(BookRepository bookRepository,
                          GameRepository gameRepository,
                          MovieRepository movieRepository,
                          UserRepository userRepository,
                          OrderRepository orderRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.gameRepository = gameRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Product> getProductByType(String category) {
        List<Product> products = new ArrayList<>();
        if (category != null) {
            switch (category) {
                case "books" -> products.addAll(getAllBooks());
                case "movies" -> products.addAll(getAllMovies());
                case "games" -> products.addAll(getAllGames());
                default -> {
                    products.addAll(getAllGames());
                    products.addAll(getAllMovies());
                    products.addAll(getAllBooks());
                }
            }
        }
        return products;
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public Movie findMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow();
    }

    public Game findGameById(Long id) {
        return gameRepository.findById(id).orElseThrow();
    }

    public List<Product> searchProducts(String search) {
        List<Product> productsFound = new ArrayList<>();

        List<Movie> movies = findMoviesBySearch(search);
        List<Game> games = findGamesBySearch(search);
        List<Book> books = findBooksBySearch(search);

        productsFound.addAll(movies);
        productsFound.addAll(games);
        productsFound.addAll(books);

        System.out.println(productsFound);

        return productsFound;
    }

    private List<Book> findBooksBySearch(String search) {
        return bookRepository.findAll().stream()
                .filter(book -> book
                        .getTitle()
                        .contains(search))
                .toList();

        //todo get titles and more?
        //todo make search in lowercase?
    }

    private List<Game> findGamesBySearch(String search) {
        return gameRepository.findAll().stream()
                .filter(game -> game
                        .getTitle()
                        .contains(search))
                .toList();
    }

    private List<Movie> findMoviesBySearch(String search) {
        return movieRepository.findAll().stream()
                .filter(movie -> movie
                        .getTitle()
                        .contains(search))
                .toList();
    }

    public List<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void addProductToCart(Long id, String productCategory, int quantity) {
        Product product = findProduct(id, productCategory);
        for (int i = 0; i < quantity; i++) {
            shoppingCart.add(product);
        }
    }

    private Product findProduct(Long id, String productCategory) {
        switch (productCategory) {
            case "Book" -> {
                Book book = bookRepository.findById(id).get();
                return book;
            }
            case "Movie" -> {
                Movie movie = movieRepository.findById(id).get();
                return movie;
            }
            case "Game" -> {
                return gameRepository.findById(id).get();
            }
        }
        return null;
    }

    public User getUserFromDatabase(String name) {
        return userRepository.findByUsername(name).get();
    }

    public int getTotalPrice() {
        return shoppingCart.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }

    public List<Product> placeOrder(String name) {
        List<Product> products = new ArrayList<>(shoppingCart);
        user = getUserFromDatabase(name);
        Order order = new Order();
        order.setBooksOrdered(getBooksFromCart(getShoppingCart()));
        order.setGamesOrdered(getGamesFromCart(getShoppingCart()));
        order.setMoviesOrdered(getMoviesFromCart(getShoppingCart()));
        order.setPlacedOn(LocalDateTime.now());
        order.setPaid(false);
        order.setTotalPrice(getTotalPrice());
        order = orderRepository.save(order);
        user.addOrder(order);
        user = userRepository.save(user);
        shoppingCart.clear();
        return products;
    }

    private List<Book> getBooksFromCart(List<Product> shoppingCart) {
        return shoppingCart.stream()
                .filter(product -> product instanceof Book)
                .map(product -> (Book) product)
                .toList();
    }

    private List<Movie> getMoviesFromCart(List<Product> shoppingCart) {
        return shoppingCart.stream()
                .filter(product -> product instanceof Movie)
                .map(product -> (Movie) product)
                .toList();
    }

    private List<Game> getGamesFromCart(List<Product> shoppingCart) {
        return shoppingCart.stream()
                .filter(product -> product instanceof Game)
                .map(product -> (Game) product)
                .toList();
    }

}
