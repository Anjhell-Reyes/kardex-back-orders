package com.kardex.infrastructure.out.mapper;

import com.kardex.domain.model.*;
import com.kardex.infrastructure.out.entity.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartEntityMapper {

    // ======= Cart <-> CartEntity =======
    @Mapping(source = "items", target = "items")
    Cart toCart(CartEntity entity);

    @Mapping(source = "items", target = "items")
    CartEntity toEntity(Cart cart);

    // ======= CartItem <-> CartItemEntity =======
    @Mapping(target = "product", expression = "java(mapProduct(cartItemEntity.getProductId()))")
    CartItem toCartItem(CartItemEntity cartItemEntity);

    @Mapping(target = "productId", source = "product", qualifiedByName = "mapProductId")
    CartItemEntity toCartItemEntity(CartItem cartItem);

    List<CartItem> toCartItems(List<CartItemEntity> entities);

    List<CartItemEntity> toCartItemEntities(List<CartItem> items);

    // ======= Helper Methods =======
    @Named("mapProductId")
    default Long mapProductId(Product product) {
        return product != null ? product.getId() : null;
    }

    default Product mapProduct(Long productId) {
        return productId != null ? new Product(productId, null, null, null, null, null) : null;
    }
}
