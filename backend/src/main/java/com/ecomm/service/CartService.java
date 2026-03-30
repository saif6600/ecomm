package com.ecomm.service;

import com.ecomm.dto.CartDtos;

public interface CartService {
    CartDtos.CartResponse getCart(Long userId);
    CartDtos.CartResponse addToCart(Long userId, CartDtos.AddToCartRequest request);
    CartDtos.CartResponse updateCartItem(Long userId, Long cartItemId, CartDtos.UpdateCartRequest request);
    CartDtos.CartResponse removeCartItem(Long userId, Long cartItemId);
}
