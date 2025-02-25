package com.example.ecommerce_platform.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                // Enable CORS using our configuration below
                .cors(Customizer.withDefaults())
                // Disable CSRF for simplicity (adjust for production as needed)
                .csrf(csrf -> csrf.disable())
                // Set session management to stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Allow the H2 console to be displayed in a frame (required by H2)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                // Define URL-based authorization rules
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/h2-console/**", "/favicon.ico").permitAll()  // Permit access to H2 console and favicon
                        .requestMatchers("/users/**", "/actuator/**").permitAll()         // Permit other public endpoints as needed
                        .anyRequest().authenticated()
                )
                // Use HTTP Basic authentication (for demonstration/testing)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // CORS configuration that allows all origins, methods, and headers.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // Allow all origins
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*")); // Allow all headers
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
