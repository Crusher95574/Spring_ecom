package com.example.ecommerce_platform.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    // Disable CSRF (useful for stateless REST APIs)
                    .csrf(csrf -> csrf.disable())
                    // Set session management to stateless
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    // Define URL-based authorization rules
                    .authorizeHttpRequests(authz -> authz
                            .requestMatchers("/users/**", "/actuator/**","/h2-console/**").permitAll()
                            .anyRequest().authenticated()
                    )
                    // Configure exception handling with a custom authentication entry point
                    .exceptionHandling(exception -> exception
                            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                    )
                    // For demonstration, we use HTTP Basic authentication.
                    .httpBasic(Customizer.withDefaults());

            return http.build();
        }
    }


