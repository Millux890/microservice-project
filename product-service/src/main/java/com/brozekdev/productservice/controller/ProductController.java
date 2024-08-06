package com.brozekdev.productservice.controller;

import com.brozekdev.productservice.dto.ProductRequest;
import com.brozekdev.productservice.dto.ProductResponse;
import com.brozekdev.productservice.exceptions.BadRequestException;
import com.brozekdev.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) throws BadRequestException {
        productService.createProduct(productRequest);
    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/product/{productName}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductByName(@PathVariable ("productName") String productName) throws Exception {
        return productService.getProductByName(productName);
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public Boolean testConnection(){
        return true;
    }

}
