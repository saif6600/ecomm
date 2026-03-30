package com.ecomm.controller;

import com.ecomm.dto.CartDtos;
import com.ecomm.service.CartService;
import com.ecomm.service.impl.CurrentUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CurrentUserService currentUserService;

    @GetMapping
    public CartDtos.CartResponse getCart(@AuthenticationPrincipal UserDetails principal) {
        return cartService.getCart(currentUserService.getUserIdByEmail(principal.getUsername()));
    }

    @PostMapping
    public CartDtos.CartResponse addToCart(@AuthenticationPrincipal UserDetails principal,
                                           @Valid @RequestBody CartDtos.AddToCartRequest request) {
        return cartService.addToCart(currentUserService.getUserIdByEmail(principal.getUsername()), request);
    }

    @PutMapping("/{cartItemId}")
    public CartDtos.CartResponse updateCart(@AuthenticationPrincipal UserDetails principal,
                                            @PathVariable Long cartItemId,
                                            @Valid @RequestBody CartDtos.UpdateCartRequest request) {
        return cartService.updateCartItem(currentUserService.getUserIdByEmail(principal.getUsername()), cartItemId, request);
    }

    @DeleteMapping("/{cartItemId}")
    public CartDtos.CartResponse removeCart(@AuthenticationPrincipal UserDetails principal,
                                            @PathVariable Long cartItemId) {
        return cartService.removeCartItem(currentUserService.getUserIdByEmail(principal.getUsername()), cartItemId);
    }
}
