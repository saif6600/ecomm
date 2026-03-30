package com.ecomm.service.impl;

import com.ecomm.dto.CartDtos;
import com.ecomm.entity.CartItem;
import com.ecomm.exception.ApiException;
import com.ecomm.exception.ResourceNotFoundException;
import com.ecomm.repository.CartItemRepository;
import com.ecomm.repository.ProductRepository;
import com.ecomm.repository.UserRepository;
import com.ecomm.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public CartDtos.CartResponse getCart(Long userId) {
        List<CartDtos.CartItemResponse> items = cartItemRepository.findByUserId(userId).stream()
                .map(this::toItemDto)
                .toList();
        BigDecimal total = items.stream().map(CartDtos.CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return CartDtos.CartResponse.builder().items(items).total(total).build();
    }

    @Override
    public CartDtos.CartResponse addToCart(Long userId, CartDtos.AddToCartRequest request) {
        var user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId));
        var product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product", request.getProductId()));
        if (product.getStock() < request.getQuantity()) {
            throw new ApiException("Insufficient stock");
        }

        var item = cartItemRepository.findByUserIdAndProductId(userId, product.getId())
                .orElse(CartItem.builder().user(user).product(product).quantity(0).build());
        item.setQuantity(item.getQuantity() + request.getQuantity());
        cartItemRepository.save(item);
        return getCart(userId);
    }

    @Override
    public CartDtos.CartResponse updateCartItem(Long userId, Long cartItemId, CartDtos.UpdateCartRequest request) {
        var item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Cart item", cartItemId));
        if (!item.getUser().getId().equals(userId)) {
            throw new ApiException("Unauthorized cart access");
        }
        item.setQuantity(request.getQuantity());
        cartItemRepository.save(item);
        return getCart(userId);
    }

    @Override
    public CartDtos.CartResponse removeCartItem(Long userId, Long cartItemId) {
        var item = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("Cart item", cartItemId));
        if (!item.getUser().getId().equals(userId)) {
            throw new ApiException("Unauthorized cart access");
        }
        cartItemRepository.delete(item);
        return getCart(userId);
    }

    private CartDtos.CartItemResponse toItemDto(CartItem item) {
        BigDecimal subtotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        return CartDtos.CartItemResponse.builder()
                .cartItemId(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .quantity(item.getQuantity())
                .subtotal(subtotal)
                .build();
    }
}
