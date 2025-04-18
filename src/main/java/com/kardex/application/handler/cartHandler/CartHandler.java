package com.kardex.application.handler.cartHandler;

import com.kardex.application.dto.cartDto.*;
import com.kardex.application.mapper.CartMapper;
import com.kardex.domain.api.ICartServicePort;
import com.kardex.domain.model.CartItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CartHandler implements ICartHandler {

    private final ICartServicePort cartServicePort;
    private final CartMapper cartMapper;

    @Override
    public CartResponse getCart(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        return cartMapper.toResponse(cartServicePort.getCartByUserId(userId));
    }

    @Override
    public void addItem(HttpServletRequest request, CartItemRequest item){
        String userId = (String) request.getAttribute("userId");
        String email = (String) request.getAttribute("email");
        CartItem cartItem = cartMapper.toCartItem(item);

        cartServicePort.addItem(userId, email, cartItem);
    }

    @Override
    public void removeItem(HttpServletRequest request, Long productId){
        String userId = (String) request.getAttribute("userId");

        cartServicePort.removeItem(userId, productId);
    }
}
