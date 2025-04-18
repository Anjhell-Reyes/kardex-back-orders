package com.kardex.infrastructure.out.adapter;

import com.kardex.domain.exception.CartNotFoundException;
import com.kardex.domain.model.Cart;
import com.kardex.domain.spi.ICartPersistencePort;
import com.kardex.infrastructure.out.mapper.CartEntityMapper;
import com.kardex.infrastructure.out.repository.ICartRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartJpaAdapter implements ICartPersistencePort {

    private final ICartRepository cartRepository;
    private final CartEntityMapper cartEntityMapper;

    @Override
    public void saveCart(Cart cart) {
        cartEntityMapper.toCart(cartRepository.save(cartEntityMapper.toEntity(cart)));
    }

    @Override
    public Cart getCart(Long cartId) {
        return  cartEntityMapper.toCart(cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new));
    }

    @Override
    public Cart getCartByUserId(String userId) {
        return cartEntityMapper.toCart(cartRepository.findByUserId(userId).orElse(null));
    }
}
