package com.brozekdev.orderservice.exceptions;

import lombok.Getter;

@Getter
public enum OrderError {

    //PRODUCT_NAME_NOT_UNIQUE("Product name is not unique!"),
    PRODUCT_DOES_NOT_EXIST("Product with code '%s' doest not exist!"),
    UNAUTHORIZED("Unauthorized!");

    final String message;

    OrderError(String message){
        this.message = message;
    }
}
