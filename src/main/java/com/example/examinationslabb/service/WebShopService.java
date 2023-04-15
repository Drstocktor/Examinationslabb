package com.example.examinationslabb.service;

import com.example.examinationslabb.model.*;
import com.example.examinationslabb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.*;

@Service
@SessionScope
public class WebShopService {
    private final BookRepository bookRepository;
    private final GameRepository gameRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private List<Product> shoppingCart = new ArrayList<>();
    private User user = new User();

    @Autowired
    public WebShopService(BookRepository bookRepository,
                          GameRepository gameRepository,
                          MovieRepository movieRepository,
                          UserRepository userRepository,
                          OrderRepository orderRepository) {
        this.bookRepository = bookRepository;
        this.gameRepository = gameRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
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

    public void addProductToCart(Long id, String productCategory, int quantity) {
        Product product = findProduct(id, productCategory);
        for (int i = 0; i < quantity; i++) {
            shoppingCart.add(product);
        }
    }

    private Product findProduct(Long id, String productCategory) {
        Map<String, JpaRepository<? extends Product, Long>> repositoryMap = Map.of(
                "Book", bookRepository,
                "Movie", movieRepository,
                "Game", gameRepository
        );

        JpaRepository<? extends Product, Long> repository = repositoryMap.get(productCategory);
        if (repository == null) {
            throw new IllegalArgumentException("Invalid product category: " + productCategory);
        }
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found"));

//        switch (productCategory) {
//            case "Book" -> {
//                return bookRepository.findById(id).get();
//            }
//            case "Movie" -> {
//                return movieRepository.findById(id).get();
//            }
//            case "Game" -> {
//                return gameRepository.findById(id).get();
//            }
//            default -> {
//            }
//        }
//        return null;
    }

    public User getUserFromDatabase(String name) {
        return userRepository.findByUsername(name).get();
    }

    public int getTotalPrice() {
        return shoppingCart.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }

    public Map<Product, Integer> placeOrder(String name) {
        Map<Product, Integer> shoppingCartMap = getShoppingCart();
        user = getUserFromDatabase(name);
        Order order = new Order();
        order.setBooksOrdered(getBooksFromCart(shoppingCart));
        order.setGamesOrdered(getGamesFromCart(shoppingCart));
        order.setMoviesOrdered(getMoviesFromCart(shoppingCart));
        order.setPlacedOn(LocalDateTime.now());
        order.setPaid(false);
        order.setTotalPrice(getTotalPrice());
        order = orderRepository.save(order);
        user.addOrder(order);
        user = userRepository.save(user);
        shoppingCart.clear();
        return shoppingCartMap;
    }

    private List<Book> getBooksFromCart(List<Product> shoppingCart) {
        return shoppingCart.stream()
                .filter(Book.class::isInstance)
                .map(Book.class::cast)
                .toList();
    }

    private List<Movie> getMoviesFromCart(List<Product> shoppingCart) {
        return shoppingCart.stream()
                .filter(Movie.class::isInstance)
                .map(Movie.class::cast)
                .toList();
    }

    private List<Game> getGamesFromCart(List<Product> shoppingCart) {
        return shoppingCart.stream()
                .filter(Game.class::isInstance)
                .map(Game.class::cast)
                .toList();
    }

    public List<Order> getPaidOrders() {
        return orderRepository.findAll()
                .stream()
                .filter(Order::isPaid)
                .toList();
    }

    public List<Order> getUnpaidOrders() {
        return orderRepository.findAll()
                .stream()
                .filter(order -> !order.isPaid())
                .toList();
    }

    public void chargeCustomer(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setPaid(true);
        orderRepository.save(order);
    }

    public void removeProductFromCart(Long productId, String productCategory, int quantity) {
        Product product = findProduct(productId, productCategory);

        Iterator<Product> iterator = shoppingCart.iterator();

        int count = 0;
        while (iterator.hasNext() && count < quantity) {
            Product p = iterator.next();
            if (p.equals(product)) {
                iterator.remove();
                count++;
            }
        }
    }

    public List<Product> getShoppingCartList() {
        return shoppingCart;
    }

    public Map<Product, Integer> getShoppingCart() {
        Map<Product, Integer> productMap = new HashMap<>();
        for (Product p : shoppingCart) {
            if (productMap.containsKey(p)) {
                productMap.put(p, productMap.get(p) + 1);
            } else {
                productMap.put(p, 1);
            }
        }
        return productMap;

    }

    public void removeAllOfCertainProductFromCart(Long productId, String productCategory) {
        Product product = findProduct(productId, productCategory);
        shoppingCart.removeIf(product1 -> product1.equals(product));
    }
}
