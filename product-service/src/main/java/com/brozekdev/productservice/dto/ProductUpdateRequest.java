package com.brozekdev.productservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductUpdateRequest {

    private String name;

    private String description;

    private Long price;
}
