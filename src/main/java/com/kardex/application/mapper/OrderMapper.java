package com.kardex.application.mapper;

import com.kardex.application.dto.cartDto.CartItemResponse;
import com.kardex.application.dto.orderDto.*;
import com.kardex.application.dto.productDto.ProductSummaryResponse;
import com.kardex.application.dto.statusDto.StatusSummaryResponse;
import com.kardex.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    Order toOrder(OrderRequest orderRequest);

    @Mapping(target = "status", expression = "java(new Status(orderRequest.getStatusId(), null))")
    Order toOrder(OrderUpdateRequest orderRequest);

    // Mapear la respuesta de la orden (con los items y productos completos)
    @Mapping(source = "status", target = "status", qualifiedByName = "toStatusSummaryResponse")
    @Mapping(target = "items", source = "items", qualifiedByName = "mapCartItemsToResponse")
    OrderResponse toResponse(Order order);

    // Para paginar las órdenes, también mapear los items
    @Mapping(source = "status", target = "status", qualifiedByName = "toStatusSummaryResponse")
    @Mapping(target = "items", source = "items", qualifiedByName = "mapCartItemsToResponse")
    OrderPaginated toOrderPaginated(Order order);

    @Mapping(target = "statusId", source = "status.statusId")
    OrderStatusHistoryResponse toOrderStatusHistoryResponse(OrderStatusHistory order);

    // Mapeo para convertir CartItems en la respuesta
    @Named("mapCartItemsToResponse")
    default List<CartItemResponse> mapCartItemsToResponse(List<CartItem> items) {
        if (items == null) return null;
        return items.stream().map(item -> new CartItemResponse(
                item.getId(),
                item.getQuantity(),
                new ProductSummaryResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getProduct().getImageUrl(),
                        new ProviderSummaryResponse(
                                item.getProduct().getProvider().getId(),
                                item.getProduct().getProvider().getCompanyName(),
                                item.getProduct().getProvider().getEmail()
                        )
                )
        )).collect(Collectors.toList());
    }

    @Named("toStatusSummaryResponse")
    default StatusSummaryResponse toStatusSummaryResponse(Status status) {
        return new StatusSummaryResponse(status.getStatusId(), status.getStatus());
    }
}
