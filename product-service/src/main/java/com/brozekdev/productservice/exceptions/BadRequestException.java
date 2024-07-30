package com.brozekdev.productservice.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends Exception{

    private String message;


    public BadRequestException(String message) {
        this.message = message;
    }
}

