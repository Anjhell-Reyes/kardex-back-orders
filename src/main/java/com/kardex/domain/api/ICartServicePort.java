package com.kardex.domain.api;

import com.kardex.domain.model.Cart;
import com.kardex.domain.model.CartItem;

public interface ICartServicePort {
    Cart getCartByUserId(String userId);

    void addItem(String userId, String email, CartItem item);

    Cart getCart(Long cartId);

    void removeItem(String userId, Long productId);

    double getTotal(Long cartId);
}
