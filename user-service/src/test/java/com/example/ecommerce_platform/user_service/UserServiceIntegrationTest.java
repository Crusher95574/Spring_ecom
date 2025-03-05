package com.example.ecommerce_platform.user_service;

import com.example.ecommerce_platform.user_service.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testUserRegistration() {
        // Create a new user registration payload
        User newUser = new User();
        newUser.setUsername("divay");
        newUser.setEmail("divay@example.com");
        newUser.setPassword("divay123");

        // POST request to create user (assuming endpoint is /users/register)
        ResponseEntity<User> response = restTemplate.postForEntity("/users/register", newUser, User.class);

        // Validate response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User createdUser = response.getBody();
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
    }

    @Test
    public void testGetUserById() {
        // First, create a user using the POST endpoint
        User newUser = new User();
        newUser.setUsername("testUser");
        newUser.setEmail("test@example.com");
        newUser.setPassword("password123");

        ResponseEntity<User> createResponse = restTemplate.postForEntity("/users/register", newUser, User.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        User createdUser = createResponse.getBody();
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());

        // Now, retrieve the user by its generated ID
        ResponseEntity<User> response = restTemplate.getForEntity("/users/" + createdUser.getId(), User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        User retrievedUser = response.getBody();
        assertNotNull(retrievedUser);
        assertEquals(createdUser.getId(), retrievedUser.getId());
    }

}
