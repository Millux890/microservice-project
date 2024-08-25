package com.brozekdev.orderservice.service;

import com.brozekdev.orderservice.config.WebClientConfig;
import com.brozekdev.orderservice.dto.OrderResponse;
import com.brozekdev.orderservice.event.OrderPlacedEvent;
import com.brozekdev.orderservice.exceptions.BadRequestException;
import com.brozekdev.orderservice.exceptions.OrderError;
import com.brozekdev.orderservice.model.Order;
import com.brozekdev.orderservice.model.OrderLineItem;
import com.brozekdev.orderservice.model.Product;
import com.brozekdev.orderservice.repository.OrderLineItemRepository;
import com.brozekdev.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceHelper {


    private final OrderLineItemRepository orderLineItemRepository;

    private final WebClient.Builder webClient;

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;


    boolean allOrderItemsHaveProducts(List<OrderLineItem> orderLineItems, List<Product> products) throws Exception {
        for (OrderLineItem orderItem : orderLineItems) {
            boolean productExists = false;
            for (Product product : products) {
                if (orderItem.getCode().equals(product.getCode())) {
                    productExists = true;
                    break;
                }
            }
            if (!productExists) {
                throw new BadRequestException(String.format(OrderError.PRODUCT_DOES_NOT_EXIST.getMessage(), orderItem.getCode()));
            }
        }
        return true;
    }

    List<Product> getProducts() throws Exception {
        List<Product> products;
        try {
            products = webClient.build()
                    .get()
                    .uri("lb://%s:%s@product-service/api/products/product",
                            WebClientConfig.username, WebClientConfig.password)
                    .headers(headers -> headers.setBasicAuth(WebClientConfig.username, WebClientConfig.password))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Product>>() {})
                    .block();
        } catch (Exception e) {
            throw new Exception("There was a problem with requesting for products!");
        }
        return products;
    }

    void saveOrder(Order order, List<OrderLineItem> orderLineItems){
        Order savedOrder = orderRepository.save(order);
        orderLineItems.forEach(item -> {
            item.setOrderId(savedOrder.getId());
        });
        orderLineItemRepository.saveAll(orderLineItems);
        kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getId(), order.getClientId()));
    }

    OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderLineItemList(order.getOrderLineItemList())
                .clientId(order.getClientId())
                .price(order.getPrice())
                .build();
    }

}
