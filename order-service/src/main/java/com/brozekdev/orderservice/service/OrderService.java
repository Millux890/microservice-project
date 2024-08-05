package com.brozekdev.orderservice.service;

import com.brozekdev.orderservice.dto.OrderLineItemDto;
import com.brozekdev.orderservice.dto.OrderRequest;
import com.brozekdev.orderservice.model.Order;
import com.brozekdev.orderservice.model.OrderLineItem;
import com.brozekdev.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemList(orderRequest.getOrderLineItemList().stream().map(this::mapToDto).toList())
                .build();

        orderRepository.save(order);
    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto){
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setCode(orderLineItemDto.getCode());
        return orderLineItem;
    }

}
