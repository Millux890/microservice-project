package com.brozekdev.orderservice.service;

import com.brozekdev.orderservice.dto.OrderLineItemDto;
import com.brozekdev.orderservice.dto.OrderRequest;
import com.brozekdev.orderservice.dto.ProductResponse;
import com.brozekdev.orderservice.event.OrderPlacedEvent;
import com.brozekdev.orderservice.model.Order;
import com.brozekdev.orderservice.model.OrderLineItem;
import com.brozekdev.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClient;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemList(orderRequest.getOrderLineItemList().stream().map(this::mapToDto).toList())
                .build();
        String productName = "samsung";

        ProductResponse result = webClient.build().get()
                .uri("lb://product-service/api/products/product/product/" + productName)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();
        if (result != null) {
            System.out.println(result);
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
            System.out.println("ORDER SENT SUCCESSFULLY");
        } else {
            throw new IllegalArgumentException("stop");
        }
    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());
        orderLineItem.setCode(orderLineItemDto.getCode());
        return orderLineItem;
    }

}
