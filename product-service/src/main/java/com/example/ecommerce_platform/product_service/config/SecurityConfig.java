package com.example.ecommerce_platform.product_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Enable CORS using our custom configuration below
                .cors(Customizer.withDefaults())
                // Disable CSRF for development (review this for production)
                .csrf(csrf -> csrf.disable())
                // Allow the H2 console to be displayed in a frame from the same origin
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                // Configure URL-based authorization rules
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/products/**", "/h2-console/**", "/favicon.ico").permitAll()
                        .anyRequest().authenticated()
                )
                // Use HTTP Basic authentication for endpoints that require authentication
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow all origins (for development; adjust for production security)
        configuration.setAllowedOrigins(List.of("*"));
        // Allow standard HTTP methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Allow all headers
        configuration.setAllowedHeaders(List.of("*"));
        // Allow credentials (cookies, authorization headers, etc.)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this configuration to all endpoints
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

