package com.inventory.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.inventory.entity.Product;
import com.inventory.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger log =
            LoggerFactory.getLogger(ProductController.class);

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product addProduct(
            @Valid @RequestBody Product product) {

        log.info("Add Product API called with product name: {}",
                product.getName());

        Product savedProduct = service.addProduct(product);

        log.info("Product added successfully with id: {}",
                savedProduct.getId());

        return savedProduct;
    }

    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {

        log.info("Update Product API called with id: {}", id);

        Product updatedProduct =
                service.updateProduct(id, product);

        log.info("Product updated successfully with id: {}",
                updatedProduct.getId());

        return updatedProduct;
    }

    @GetMapping("/{id}")
    public Product getProduct(
            @PathVariable Long id) {

        log.info("Get Product By Id API called with id: {}", id);

        return service.getProductById(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {

        log.info("Get All Products API called");

        return service.getAllProducts();
    }

    @PatchMapping("/{id}/increase/{quantity}")
    public Product increaseStock(
            @PathVariable Long id,
            @PathVariable int quantity) {

        log.info(
                "Increase Stock API called for product id: {}, quantity: {}",
                id, quantity);

        Product product =
                service.increaseStock(id, quantity);

        log.info("Stock increased successfully for product id: {}",
                id);

        return product;
    }

    @PatchMapping("/{id}/decrease/{quantity}")
    public Product decreaseStock(
            @PathVariable Long id,
            @PathVariable int quantity) {

        log.info(
                "Decrease Stock API called for product id: {}, quantity: {}",
                id, quantity);

        Product product =
                service.decreaseStock(id, quantity);

        log.info("Stock decreased successfully for product id: {}",
                id);

        return product;
    }

    @GetMapping("/low-stock")
    public List<Product> lowStock() {

        log.info("Low Stock Products API called");

        return service.getLowStockProducts();
    }
}
