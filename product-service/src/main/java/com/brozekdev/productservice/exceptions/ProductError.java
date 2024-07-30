package com.brozekdev.productservice.exceptions;

import lombok.Getter;

@Getter
public enum ProductError {

    PRODUCT_NAME_NOT_UNIQUE("Product name is not unique!");

    final String message;

    ProductError(String message){
        this.message = message;
    }
}
