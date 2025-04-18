package com.kardex.domain.usecase;

import com.kardex.domain.api.ICartServicePort;
import com.kardex.domain.exception.*;
import com.kardex.domain.model.CartItem;
import com.kardex.domain.model.Cart;
import com.kardex.domain.model.Product;
import com.kardex.domain.spi.*;

import java.util.ArrayList;
import java.util.List;


public class CartUseCase implements ICartServicePort {

    private final ICartPersistencePort cartPersistencePort;
    private final IProductPersistencePort productPersistencePort;

    public CartUseCase(ICartPersistencePort cartPersistencePort, IProductPersistencePort productPersistencePort) {
        this.cartPersistencePort = cartPersistencePort;
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public void addItem(String userId, String email, CartItem item){

        if(userId == null){
            throw new UserIdNotNullException();
        }
        if(email == null){
            throw new CustomerEmailNotNullException();
        }
        if(item.getQuantity() == null){
            throw new QuantityNotNullException();
        }
        if (item.getProduct() == null || item.getProduct().getId() == null) {
            throw new ProductNotFoundException();
        }

        Cart cart = cartPersistencePort.getCartByUserId(userId);
        if (cart == null) {
            cart = new Cart(userId, email);
        }

        validateUserPermission(userId, cart);

        Product product = productPersistencePort.getProduct(item.getProduct().getId());
        if (product == null) {
            throw new ProductNotFoundException();
        }

        for (CartItem existingItem : cart.getItems()) {
            if (existingItem.getProduct().getId().equals(item.getProduct().getId())) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                cartPersistencePort.saveCart(cart);
                return;
            }
        }

        cart.getItems().add(item);
//        item.setCart(cart);
        cartPersistencePort.saveCart(cart);
    }

    @Override
    public Cart getCartByUserId(String userId) {
        Cart cart = cartPersistencePort.getCartByUserId(userId);

        if (cart == null) {
            throw new CartNotFoundException();
        }

        validateUserPermission(userId, cart);

        return getCart(cart);
    }

    @Override
    public Cart getCart(Long cartId) {
        Cart cart = cartPersistencePort.getCart(cartId);

        return getCart(cart);
    }

    @Override
    public void removeItem(String userId, Long productId) {
        Cart cart = cartPersistencePort.getCartByUserId(userId);

        validateUserPermission(userId, cart);

        // Verificar si existe el producto en el carrito
        boolean exists = cart.getItems().stream()
                .anyMatch(i -> i.getProduct() != null && i.getProduct().getId().equals(productId));

        if (!exists) {
            throw new ProductNotFoundException();
        }

        // Si existe, lo eliminamos
        cart.getItems().removeIf(i -> i.getProduct() != null && i.getProduct().getId().equals(productId));

        cartPersistencePort.saveCart(cart);
    }

    @Override
    public double getTotal(Long cartId) {
        Cart originalCart = cartPersistencePort.getCart(cartId);

        Cart enrichedCart = getCart(originalCart);

        return enrichedCart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    private Cart getCart(Cart cart) {
        if (cart.getProductIds() == null || cart.getProductIds().isEmpty()) {
            throw new ProductNotFoundException();
        }

        List<Long> productIds = cart.getProductIds();

        List<Product> products = new ArrayList<>();
        for (Long productId : productIds) {
            Product product = productPersistencePort.getProduct(productId);
            if (product == null) {
                throw new ProductNotFoundException();
            }
            products.add(product);
        }

        for (CartItem item : cart.getItems()) {
            // Buscamos el producto correspondiente para cada CartItem
            Product product = products.stream()
                    .filter(p -> p.getId().equals(item.getProduct().getId()))
                    .findFirst()
                    .orElseThrow(ProductNotFoundException::new);
            item.setProduct(product);  // Asignamos el producto al CartItem
        }

        return cart;
    }

    private void validateUserPermission(String userId, Cart cart) {
        if (!cart.getUserId().equals(userId)) {
            throw new UserForbiddenException();
        }
    }
}
