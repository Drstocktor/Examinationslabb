package com.example.examinationslabb;

import com.example.examinationslabb.model.User;
import com.example.examinationslabb.model.UserType;
import com.example.examinationslabb.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ExaminationslabbApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExaminationslabbApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository users, PasswordEncoder passwordEncoder) {
        return args -> {
            users.save(new User("Victor", passwordEncoder.encode("victor"), UserType.CUSTOMER));
            users.save(new User("Admin", passwordEncoder.encode("admin"), UserType.ADMIN));
        };
    }
}
