package com.kardex.infrastructure.out.mapper;

import com.kardex.domain.model.CartItem;
import com.kardex.domain.model.Order;
import com.kardex.domain.model.Product;
import com.kardex.infrastructure.out.entity.CartItemEntity;
import com.kardex.infrastructure.out.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderEntityMapper {

    @Mapping(target = "items", source = "items", qualifiedByName = "mapCartItems")
    OrderEntity toEntity(Order order);

    @Mapping(target = "items", source = "items", qualifiedByName = "mapCartItemsReverse")
    Order toOrder(OrderEntity orderEntity);

    @Named("mapCartItems")
    default List<CartItemEntity> mapCartItems(List<CartItem> items) {
        if (items == null) return null;
        return items.stream().map(item -> {
            CartItemEntity entity = new CartItemEntity();
            entity.setQuantity(item.getQuantity());
            entity.setProductId(item.getProduct() != null ? item.getProduct().getId() : null);
            return entity;
        }).collect(Collectors.toList());
    }

    @Named("mapCartItemsReverse")
    default List<CartItem> mapCartItemsReverse(List<CartItemEntity> entities) {
        if (entities == null) return null;
        return entities.stream().map(entity -> new CartItem(null, entity.getQuantity(),
                new Product(entity.getProductId(), null, null, null, null, null))).collect(Collectors.toList());
    }
}
