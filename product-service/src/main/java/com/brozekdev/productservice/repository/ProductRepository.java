package com.brozekdev.productservice.repository;

import com.brozekdev.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsByName(String name);
}
