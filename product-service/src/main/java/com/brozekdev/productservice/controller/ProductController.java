package com.brozekdev.productservice.controller;

import com.brozekdev.productservice.dto.ProductRequest;
import com.brozekdev.productservice.dto.ProductResponse;
import com.brozekdev.productservice.exceptions.BadRequestException;
import com.brozekdev.productservice.dto.ProductUpdateRequest;
import com.brozekdev.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) throws BadRequestException {
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/product/{productCode}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductByName(@PathVariable ("productCode") String productCode) throws Exception {
        return productService.getProductByCode(productCode);
    }

    @PatchMapping("/product/{productCode}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateProductByCode(@PathVariable ("productCode") String productCode, @RequestBody ProductUpdateRequest productUpdate) throws Exception {
        productService.updateProductByCode(productCode, productUpdate);
    }

}
