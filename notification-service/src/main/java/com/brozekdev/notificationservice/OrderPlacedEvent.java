package com.brozekdev.notificationservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent {

    private String orderNumber;

    private Long clientId;

}
