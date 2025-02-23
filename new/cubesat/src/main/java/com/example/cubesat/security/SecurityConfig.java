package com.example.cubesat.security;

import com.example.cubesat.model.User;
import com.example.cubesat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/user/signup").permitAll()
                        .requestMatchers("/api/user/login").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/ws/cubesat/**").permitAll()
                        .requestMatchers("index.html/**").permitAll()
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/swagger-ui/**").authenticated()
                        .requestMatchers("/api/cubesat/**").authenticated()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions().disable())
                .httpBasic();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent() && user.get().getPassword().equals(password)) {
                return new UsernamePasswordAuthenticationToken(user.get(), null, new ArrayList<>());
            } else {
                throw new BadCredentialsException("Invalid credentials");
            }
        };
    }

}
