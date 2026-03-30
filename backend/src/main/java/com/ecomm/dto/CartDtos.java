package com.ecomm.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

public class CartDtos {
    @Getter @Setter
    public static class AddToCartRequest {
        @NotNull private Long productId;
        @NotNull @Min(1) private Integer quantity;
    }

    @Getter @Setter
    public static class UpdateCartRequest {
        @NotNull @Min(1) private Integer quantity;
    }

    @Getter @Builder @AllArgsConstructor
    public static class CartItemResponse {
        private Long cartItemId;
        private Long productId;
        private String productName;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subtotal;
    }

    @Getter @Builder @AllArgsConstructor
    public static class CartResponse {
        private List<CartItemResponse> items;
        private BigDecimal total;
    }
}
