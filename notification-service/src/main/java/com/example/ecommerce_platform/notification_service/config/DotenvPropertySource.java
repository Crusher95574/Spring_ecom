package com.example.ecommerce_platform.notification_service.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.core.env.PropertySource;

public class DotenvPropertySource extends PropertySource<Dotenv> {

    public DotenvPropertySource(String name, Dotenv source) {
        super(name, source);
    }

    @Override
    public Object getProperty(String name) {
        return this.source.get(name);
    }
}
