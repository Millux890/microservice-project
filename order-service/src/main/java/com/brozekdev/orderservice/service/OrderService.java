package com.brozekdev.orderservice.service;

import com.brozekdev.orderservice.dto.OrderRequest;
import com.brozekdev.orderservice.dto.OrderResponse;
import com.brozekdev.orderservice.model.Order;
import com.brozekdev.orderservice.model.OrderLineItem;
import com.brozekdev.orderservice.model.Product;
import com.brozekdev.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;


    private final OrderServiceHelper orderServiceHelper;

    public List<OrderResponse> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderServiceHelper::mapToOrderResponse).toList();
    }

    public void placeOrder(OrderRequest orderRequest) throws Exception {
        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemList().stream().toList();
        Order order = Order.builder()
                .price(orderRequest.getPrice())
                .clientId(orderRequest.getClientId())
                .build();
        List<Product> products = orderServiceHelper.getProducts();
        if (orderServiceHelper.allOrderItemsHaveProducts(orderLineItems, products.stream().toList())) {
            orderServiceHelper.saveOrder(order, orderLineItems);
        }
    }

}
