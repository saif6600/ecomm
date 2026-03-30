package com.ecomm.service;

import com.ecomm.dto.ProductDtos;

import java.util.List;

public interface ProductService {
    List<ProductDtos.ProductResponse> getProducts(String search, String category);
    ProductDtos.ProductResponse getProduct(Long id);
    ProductDtos.ProductResponse createProduct(ProductDtos.ProductRequest request);
    ProductDtos.ProductResponse updateProduct(Long id, ProductDtos.ProductRequest request);
    void deleteProduct(Long id);
}
