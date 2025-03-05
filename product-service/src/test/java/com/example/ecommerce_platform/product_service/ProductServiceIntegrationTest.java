package com.example.ecommerce_platform.product_service;

import com.example.ecommerce_platform.product_service.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Product validProduct;

    @BeforeEach
    public void setUp() {
        // Create a valid product with all required fields
        validProduct = new Product();
        validProduct.setName("Smartphone");
        validProduct.setDescription("Latest smartphone with advanced features");
        validProduct.setPrice(699.99);
        validProduct.setStock(50);               // Required: non-null and non-negative
        validProduct.setBrand("BrandX");           // Required: Brand is required
        validProduct.setCategory("Electronics");   // Required: Category is required
        validProduct.setImageUrl("http://example.com/smartphone.png");
    }

    @Test
    public void testListProducts() {
        ResponseEntity<Product[]> response = restTemplate.getForEntity("/products", Product[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Product[] products = response.getBody();
        assertNotNull(products);
        // Further assertions can check that the list is not empty
    }

    @Test
    public void testCreateAndGetProduct() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> request = new HttpEntity<>(validProduct, headers);

        // Send a POST request to create the product
        ResponseEntity<Product> createResponse = restTemplate.postForEntity("/products", request, Product.class);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode(), "Product creation failed");
        Product createdProduct = createResponse.getBody();
        assertNotNull(createdProduct, "Created product should not be null");
        assertNotNull(createdProduct.getId(), "Product ID should be generated");

        // Retrieve the product using its generated ID
        ResponseEntity<Product> getResponse = restTemplate.getForEntity("/products/" + createdProduct.getId(), Product.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode(), "Expected product to be found");
        Product retrievedProduct = getResponse.getBody();
        assertNotNull(retrievedProduct, "Retrieved product should not be null");
        assertEquals(createdProduct.getId(), retrievedProduct.getId(), "Retrieved product ID should match");
    }
}
