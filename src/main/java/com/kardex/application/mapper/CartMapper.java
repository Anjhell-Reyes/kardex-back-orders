package com.kardex.application.mapper;

import com.kardex.application.dto.cartDto.CartItemRequest;
import com.kardex.application.dto.cartDto.CartItemResponse;
import com.kardex.application.dto.cartDto.CartResponse;
import com.kardex.application.dto.productDto.ProductSummaryResponse;
import com.kardex.domain.model.Cart;
import com.kardex.domain.model.CartItem;
import com.kardex.domain.model.Product;
import com.kardex.domain.model.ProviderSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    @Mapping(source = "items", target = "items")
    @Mapping(target = "totalPrice", source = ".", qualifiedByName = "calculateTotal")
    CartResponse toResponse(Cart cart);

    default CartItemResponse toCartItemResponse(CartItem item) {
        return new CartItemResponse(
                item.getId(),
                item.getQuantity(),
                toProductSummaryResponse(item.getProduct())
        );
    }

    default List<CartItemResponse> toCartItemResponseList(List<CartItem> items) {
        return items.stream()
                .map(this::toCartItemResponse)
                .collect(Collectors.toList());
    }

    default ProductSummaryResponse toProductSummaryResponse(Product product) {
        return new ProductSummaryResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                new ProviderSummaryResponse(
                        product.getProvider().getId(),
                        product.getProvider().getCompanyName(),
                        product.getProvider().getEmail()
                )
        );
    }

    @Named("calculateTotal")
    default Double calculateTotal(Cart cart) {
        return cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    @Named("toCartItem")
    default CartItem toCartItem(CartItemRequest request) {
        Product product = new Product(request.getProductId(), null, null, null, null, null);
        return new CartItem(null, request.getQuantity(), product);
    }
}
