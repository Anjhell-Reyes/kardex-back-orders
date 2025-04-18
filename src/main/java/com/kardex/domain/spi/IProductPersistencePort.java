package com.kardex.domain.spi;

import com.kardex.domain.model.Product;

public interface IProductPersistencePort {
    Product getProduct(Long productId);
}
