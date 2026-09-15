package com.example.productservice.service;

import java.util.List;

import com.example.productservice.entity.Product;

public interface ProductService {

    Product createProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(Integer id);

    Product updateProduct(Integer id, Product product);

    void deleteProduct(Integer id);
}