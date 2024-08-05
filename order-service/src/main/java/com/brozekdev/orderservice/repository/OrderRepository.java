package com.brozekdev.orderservice.repository;

import com.brozekdev.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository  extends MongoRepository<Order, String> {
}
