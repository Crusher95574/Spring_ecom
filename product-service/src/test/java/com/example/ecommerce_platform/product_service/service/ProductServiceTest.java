package com.example.ecommerce_platform.product_service.service;

import com.example.ecommerce_platform.product_service.model.Product;
import com.example.ecommerce_platform.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService; // Contains business logic for product operations

    private Product validProduct;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

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
    public void testAddProduct() {
        // Simulate repository behavior
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setId("generate-id");
            return savedProduct;
        });

        // Act: Add product via service
        Product savedProduct = productService.createProduct(validProduct);

        // Assert: Check that the product is saved and ID is set
        assertNotNull(savedProduct.getId());
        // Updated expected value to match the validProduct name ("Smartphone")
        assertEquals("Smartphone", savedProduct.getName());
    }
}

