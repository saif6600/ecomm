package com.ecomm.dto;

import com.ecomm.entity.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDtos {
    @Getter @Builder @AllArgsConstructor
    public static class OrderItemResponse {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
    }

    @Getter @Builder @AllArgsConstructor
    public static class OrderResponse {
        private Long id;
        private BigDecimal totalAmount;
        private OrderStatus status;
        private LocalDateTime createdAt;
        private List<OrderItemResponse> items;
    }
}
