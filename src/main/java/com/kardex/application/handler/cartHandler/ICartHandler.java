package com.kardex.application.handler.cartHandler;

import com.kardex.application.dto.cartDto.CartItemRequest;
import com.kardex.application.dto.cartDto.CartResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface ICartHandler {
    CartResponse getCart(HttpServletRequest request);

    void addItem(HttpServletRequest request, CartItemRequest item);

    void removeItem(HttpServletRequest request, Long productId);
}
