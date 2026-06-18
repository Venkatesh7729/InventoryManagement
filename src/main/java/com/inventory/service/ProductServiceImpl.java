package com.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inventory.entity.Product;
import com.inventory.exception.DuplicateProductException;
import com.inventory.exception.ProductNotFoundException;
import com.inventory.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;

    public ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public Product addProduct(Product product) {

        if (repo.findByName(product.getName()).isPresent()) {
            throw new DuplicateProductException(
                    "Product name already exists");
        }

        return repo.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {

        Product existing = repo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        existing.setName(product.getName());
        existing.setCategory(product.getCategory());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());

        return repo.save(existing);
    }

    @Override
    public Product getProductById(Long id) {

        return repo.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    @Override
    public Product increaseStock(Long id, int quantity) {

        Product product = getProductById(id);

        product.setQuantity(product.getQuantity() + quantity);

        return repo.save(product);
    }

    @Override
    public Product decreaseStock(Long id, int quantity) {

        Product product = getProductById(id);

        if (product.getQuantity() < quantity) {
            throw new RuntimeException(
                    "Insufficient stock available");
        }

        product.setQuantity(product.getQuantity() - quantity);

        return repo.save(product);
    }

    @Override
    public List<Product> getLowStockProducts() {

        return repo.findByQuantityLessThan(5);
    }
}