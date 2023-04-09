package com.example.examinationslabb.security;

import com.example.examinationslabb.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/add").hasRole("ADMIN")
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated())
                .userDetailsService(userService)
                .formLogin(form -> form
                        .loginPage("/login")
                        .successForwardUrl("/home"))
                .build();


//                .authorizeHttpRequests()
//                .requestMatchers("/login").permitAll()
//                .anyRequest().authenticated()
//                .and().userDetailsService(userService)
//                .formLogin().loginPage("/login")
//                .defaultSuccessUrl("/home")
//                .and()
//                .logout()
//                .permitAll().and().build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
