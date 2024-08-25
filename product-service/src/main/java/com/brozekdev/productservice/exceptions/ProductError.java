package com.brozekdev.productservice.exceptions;

import lombok.Getter;

@Getter
public enum ProductError {

    PRODUCT_NAME_NOT_UNIQUE("Product name is not unique!"),
    PRODUCT_CODE_NOT_UNIQUE("Product code is not unique!"),
    PRODUCT_DOES_NOT_EXIST("Product with code '%s' doest not exist!"),
    UNAUTHORIZED("Unauthorized!");

    final String message;

    ProductError(String message){
        this.message = message;
    }
}
