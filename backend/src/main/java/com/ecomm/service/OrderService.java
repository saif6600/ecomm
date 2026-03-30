package com.ecomm.service;

import com.ecomm.dto.OrderDtos;

import java.util.List;

public interface OrderService {
    OrderDtos.OrderResponse placeOrder(Long userId);
    List<OrderDtos.OrderResponse> getMyOrders(Long userId);
    List<OrderDtos.OrderResponse> getAllOrders();
}
