package com.ecomm.service.impl;

import com.ecomm.dto.ProductDtos;
import com.ecomm.entity.Product;
import com.ecomm.exception.ResourceNotFoundException;
import com.ecomm.repository.ProductRepository;
import com.ecomm.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductDtos.ProductResponse> getProducts(String search, String category) {
        return productRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(
                search == null ? "" : search,
                category == null ? "" : category
        ).stream().map(this::toDto).toList();
    }

    @Override
    public ProductDtos.ProductResponse getProduct(Long id) {
        return toDto(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", id)));
    }

    @Override
    public ProductDtos.ProductResponse createProduct(ProductDtos.ProductRequest request) {
        Product saved = productRepository.save(Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .stock(request.getStock())
                .imageUrl(request.getImageUrl())
                .build());
        return toDto(saved);
    }

    @Override
    public ProductDtos.ProductResponse updateProduct(Long id, ProductDtos.ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", id));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        return toDto(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", id);
        }
        productRepository.deleteById(id);
    }

    private ProductDtos.ProductResponse toDto(Product p) {
        return ProductDtos.ProductResponse.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .category(p.getCategory())
                .price(p.getPrice())
                .stock(p.getStock())
                .imageUrl(p.getImageUrl())
                .build();
    }
}
