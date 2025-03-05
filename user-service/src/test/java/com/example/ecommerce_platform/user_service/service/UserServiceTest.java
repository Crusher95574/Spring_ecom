package com.example.ecommerce_platform.user_service.service;

import com.example.ecommerce_platform.user_service.model.User;
import com.example.ecommerce_platform.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService; // Assuming this class contains business logic for creating a user

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser_Success() {
        // Arrange: Create a sample user
        User user = new User("divay", "divay@example.com", "divay123");

        // Simulate repository save behavior
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            // Simulate an ID assignment (if your entity uses auto-generated IDs)
            savedUser.setId("generated-id");
            return savedUser;
        });

        // Act: Create user via the service
        User createdUser = userService.createUser(user);

        // Assert: Validate that the user was created correctly
        assertNotNull(createdUser.getId());
        assertEquals("divay", createdUser.getUsername());
        assertEquals("divay@example.com", createdUser.getEmail());
    }
}
