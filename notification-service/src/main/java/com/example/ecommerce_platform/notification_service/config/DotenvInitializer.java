package com.example.ecommerce_platform.notification_service.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class DotenvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // Adjust the directory to where your .env file is located.
        Dotenv dotenv = Dotenv.configure()
                .directory("../")  // For example, if .env is in the project root and your module is in notification-service.
                .ignoreIfMissing() // Optional: don't fail if .env is missing
                .load();
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        // Add the Dotenv property source with highest priority
        environment.getPropertySources().addFirst(new DotenvPropertySource("dotenv", dotenv));
    }
}
