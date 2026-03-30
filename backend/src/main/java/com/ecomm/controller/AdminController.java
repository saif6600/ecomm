package com.ecomm.controller;

import com.ecomm.dto.OrderDtos;
import com.ecomm.dto.UserDtos;
import com.ecomm.service.OrderService;
import com.ecomm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/users")
    public List<UserDtos.UserResponse> users() {
        return userService.getAllUsers();
    }

    @GetMapping("/orders")
    public List<OrderDtos.OrderResponse> orders() {
        return orderService.getAllOrders();
    }
}
