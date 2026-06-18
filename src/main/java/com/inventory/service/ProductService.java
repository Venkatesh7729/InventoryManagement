package com.inventory.service;

import java.util.List;

import com.inventory.entity.Product;

public interface ProductService {

    Product addProduct(Product product);

    Product updateProduct(Long id, Product product);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    Product increaseStock(Long id, int quantity);

    Product decreaseStock(Long id, int quantity);

    List<Product> getLowStockProducts();
}