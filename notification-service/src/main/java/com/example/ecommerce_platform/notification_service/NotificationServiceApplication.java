package com.example.ecommerce_platform.notification_service;

import com.example.ecommerce_platform.notification_service.config.DotenvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NotificationServiceApplication.class);
        // Add our custom initializer to load .env as a property source
        app.addInitializers(new DotenvInitializer());
        app.run(args);
    }
}
