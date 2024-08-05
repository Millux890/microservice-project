package com.brozekdev.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document // mongoDb mapping
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {

    @Id
    private String id;
    private String orderNumber;
    private List<OrderLineItem> orderLineItemList;
    private BigDecimal price;
}
