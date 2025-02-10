package com.example.ecommerce_platform.product_service.service;

import com.example.ecommerce_platform.product_service.model.Product;
import com.example.ecommerce_platform.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    //get all products
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    //get product by id
    public Optional<Product> getProductById(String id){
        return productRepository.findById(id);
    }

    //create new user
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    //update existing product
    public Product updateProduct(String id,Product productDetails){
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setBrand(productDetails.getBrand());
            product.setCategory(productDetails.getCategory());
            product.setStock(productDetails.getStock());
            product.setImageUrl(productDetails.getImageUrl());
            product.setPrice(productDetails.getPrice());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    //delete product by id
    public void deleteProductId(String id){
         productRepository.deleteById(id);
    }
}
