package com.brozekdev.orderservice.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name="cst_order_line_items")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private Long price;

    private Integer quantity;

    @Column(name = "order_id", nullable = false)
    private Long orderId;
}
