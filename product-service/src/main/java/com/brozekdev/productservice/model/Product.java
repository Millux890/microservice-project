package com.brozekdev.productservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document // mongoDb mapping
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotNull
    private String code;

    @Indexed(unique = true)
    @NotNull
    private String name;

    private String description;

    private Long price;
}

