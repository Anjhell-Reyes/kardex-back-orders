package com.kardex.domain.spi;


import com.kardex.domain.model.Cart;

public interface ICartPersistencePort {

    Cart getCartByUserId(String userId);

    void saveCart(Cart cart);

    Cart getCart(Long cartId);
}
