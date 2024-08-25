package com.brozekdev.productservice.service;

import com.brozekdev.productservice.dto.ProductRequest;
import com.brozekdev.productservice.dto.ProductResponse;
import com.brozekdev.productservice.exceptions.BadRequestException;
import com.brozekdev.productservice.exceptions.ProductError;
import com.brozekdev.productservice.model.Product;
import com.brozekdev.productservice.dto.ProductUpdateRequest;
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
                .code(productRequest.getCode())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        if (productRepository.existsByName(product.getName())) {
            throw new BadRequestException(ProductError.PRODUCT_NAME_NOT_UNIQUE.getMessage());
        }
        if (productRepository.existsByCode(product.getCode())) {
            throw new BadRequestException(ProductError.PRODUCT_CODE_NOT_UNIQUE.getMessage());
        }
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    public void updateProductByCode(String code, ProductUpdateRequest productUpdate) throws BadRequestException {
        Product existingProduct;
        try {
            existingProduct = productRepository.findProductByCode(code);
        } catch (Exception e) {
            throw new BadRequestException(ProductError.PRODUCT_DOES_NOT_EXIST.getMessage());
        }
        if(existingProduct == null){
            throw new BadRequestException(String.format(ProductError.PRODUCT_DOES_NOT_EXIST.getMessage(), code));
        }
        updateProduct(existingProduct, productUpdate);
        productRepository.save(existingProduct);

    }

    private void updateProduct(Product existingProduct, ProductUpdateRequest productUpdate){
        if(productUpdate.getName() != null){
            existingProduct.setName(productUpdate.getName());
        }
        if(productUpdate.getDescription() != null){
            existingProduct.setDescription(productUpdate.getDescription());
        }
        if(productUpdate.getPrice() != null){
            existingProduct.setPrice(productUpdate.getPrice());
        }
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .code(product.getCode())
                .build();
    }

    public ProductResponse getProductByCode(String name) throws Exception {
        Product existingProduct;
        try {
            existingProduct = productRepository.findProductByCode(name);
        } catch (Exception e) {
            throw new BadRequestException(ProductError.PRODUCT_DOES_NOT_EXIST.getMessage());
        }
        if(existingProduct == null){
            throw new BadRequestException(String.format(ProductError.PRODUCT_DOES_NOT_EXIST.getMessage(), name));
        }
        return ProductResponse.builder()
                .description(existingProduct.getDescription())
                .id(existingProduct.getId())
                .name(existingProduct.getName())
                .price(existingProduct.getPrice())
                .code(existingProduct.getCode())
                .build();
    }

}
