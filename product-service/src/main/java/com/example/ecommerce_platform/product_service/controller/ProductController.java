package com.example.ecommerce_platform.product_service.controller;

import com.example.ecommerce_platform.product_service.model.Product;
import com.example.ecommerce_platform.product_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    //Get product
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //Get product by id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id){
        return productService.getProductById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    //Create a product
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product){
        return ResponseEntity.ok(productService.createProduct(product));
    }

    //Update existing product by id
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id,@RequestBody Product product){
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    //Delete product by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable String id){
        productService.deleteProductId(id);
        return ResponseEntity.noContent().build();
    }
}
