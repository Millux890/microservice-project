package com.brozekdev.orderservice.dto;

import com.brozekdev.orderservice.model.OrderLineItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private List<OrderLineItem> orderLineItemList;
    private Long clientId;
    private Long price;

}
