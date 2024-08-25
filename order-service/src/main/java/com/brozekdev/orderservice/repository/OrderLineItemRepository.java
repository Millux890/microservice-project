package com.brozekdev.orderservice.repository;

import com.brozekdev.orderservice.model.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Long> {

}
