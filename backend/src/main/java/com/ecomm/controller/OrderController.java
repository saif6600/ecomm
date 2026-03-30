package com.ecomm.controller;

import com.ecomm.dto.OrderDtos;
import com.ecomm.service.OrderService;
import com.ecomm.service.impl.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CurrentUserService currentUserService;

    @PostMapping
    public OrderDtos.OrderResponse placeOrder(@AuthenticationPrincipal UserDetails principal) {
        return orderService.placeOrder(currentUserService.getUserIdByEmail(principal.getUsername()));
    }

    @GetMapping("/me")
    public List<OrderDtos.OrderResponse> myOrders(@AuthenticationPrincipal UserDetails principal) {
        return orderService.getMyOrders(currentUserService.getUserIdByEmail(principal.getUsername()));
    }
}
