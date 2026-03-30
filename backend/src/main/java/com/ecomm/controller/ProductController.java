package com.ecomm.controller;

import com.ecomm.dto.ProductDtos;
import com.ecomm.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductDtos.ProductResponse> list(@RequestParam(required = false) String search,
                                                  @RequestParam(required = false) String category) {
        return productService.getProducts(search, category);
    }

    @GetMapping("/{id}")
    public ProductDtos.ProductResponse get(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping("/admin")
    public ProductDtos.ProductResponse create(@Valid @RequestBody ProductDtos.ProductRequest request) {
        return productService.createProduct(request);
    }

    @PutMapping("/admin/{id}")
    public ProductDtos.ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductDtos.ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/admin/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
