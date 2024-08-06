package com.brozekdev.productservice.service;

import com.brozekdev.productservice.dto.ProductRequest;
import com.brozekdev.productservice.dto.ProductResponse;
import com.brozekdev.productservice.exceptions.BadRequestException;
import com.brozekdev.productservice.exceptions.ProductError;
import com.brozekdev.productservice.model.Product;
import com.brozekdev.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void createProduct(ProductRequest productRequest) throws BadRequestException {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        if (productRepository.existsByName(product.getName())) {
            throw new BadRequestException(ProductError.PRODUCT_NAME_NOT_UNIQUE.getMessage());
        }
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public ProductResponse getProductByName(String name) throws Exception {
        Product existingProduct;
        try {
            existingProduct = productRepository.findFirstByName(name);
        } catch (Exception e) {
            throw new Exception("product does not exist");
        }
        return ProductResponse.builder()
                .description(existingProduct.getDescription())
                .id(existingProduct.getId())
                .name(existingProduct.getName())
                .price(existingProduct.getPrice())
                .build();
    }

}
