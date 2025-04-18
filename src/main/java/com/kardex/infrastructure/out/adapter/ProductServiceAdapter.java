package com.kardex.infrastructure.out.adapter;

import com.kardex.domain.model.Product;
import com.kardex.domain.spi.IProductPersistencePort;
import com.kardex.infrastructure.out.feign.IProductClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductServiceAdapter implements IProductPersistencePort {

    private final IProductClient productClient;

    @Override
    public Product getProduct(Long productId) {
        return productClient.getProduct(productId);
    }
}
