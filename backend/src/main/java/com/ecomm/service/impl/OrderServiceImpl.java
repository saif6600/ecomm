package com.ecomm.service.impl;

import com.ecomm.dto.OrderDtos;
import com.ecomm.entity.Order;
import com.ecomm.entity.OrderItem;
import com.ecomm.entity.OrderStatus;
import com.ecomm.exception.ApiException;
import com.ecomm.repository.CartItemRepository;
import com.ecomm.repository.OrderRepository;
import com.ecomm.repository.UserRepository;
import com.ecomm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public OrderDtos.OrderResponse placeOrder(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new ApiException("User not found"));
        var cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new ApiException("Cart is empty");
        }

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        List<OrderItem> orderItems = cartItems.stream().map(ci -> {
            if (ci.getProduct().getStock() < ci.getQuantity()) {
                throw new ApiException("Insufficient stock for product: " + ci.getProduct().getName());
            }
            ci.getProduct().setStock(ci.getProduct().getStock() - ci.getQuantity());
            return OrderItem.builder()
                    .order(order)
                    .product(ci.getProduct())
                    .quantity(ci.getQuantity())
                    .price(ci.getProduct().getPrice())
                    .build();
        }).toList();

        BigDecimal total = orderItems.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setItems(orderItems);
        order.setTotalAmount(total);
        Order saved = orderRepository.save(order);
        cartItemRepository.deleteByUserId(userId);
        return toDto(saved);
    }

    @Override
    public List<OrderDtos.OrderResponse> getMyOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(this::toDto).toList();
    }

    @Override
    public List<OrderDtos.OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toDto).toList();
    }

    private OrderDtos.OrderResponse toDto(Order order) {
        return OrderDtos.OrderResponse.builder()
                .id(order.getId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .items(order.getItems().stream().map(i -> OrderDtos.OrderItemResponse.builder()
                        .productId(i.getProduct().getId())
                        .productName(i.getProduct().getName())
                        .quantity(i.getQuantity())
                        .price(i.getPrice())
                        .build()).toList())
                .build();
    }
}
