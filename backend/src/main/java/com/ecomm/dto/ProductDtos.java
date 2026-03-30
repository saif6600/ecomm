package com.ecomm.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

public class ProductDtos {
    @Getter @Setter
    public static class ProductRequest {
        @NotBlank private String name;
        @NotBlank private String description;
        @NotBlank private String category;
        @NotNull @Min(0) private BigDecimal price;
        @NotNull @Min(0) private Integer stock;
        private String imageUrl;
    }

    @Getter @Builder @AllArgsConstructor
    public static class ProductResponse {
        private Long id;
        private String name;
        private String description;
        private String category;
        private BigDecimal price;
        private Integer stock;
        private String imageUrl;
    }
}
