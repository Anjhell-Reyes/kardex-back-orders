package com.kardex.infrastructure.out.mapper;

import com.kardex.domain.model.OrderStatusHistory;
import com.kardex.infrastructure.out.entity.OrderStatusHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OrderStatusHistoryEntityMapper {

    OrderStatusHistoryEntity toEntity(OrderStatusHistory order);
    OrderStatusHistory toOrder(OrderStatusHistoryEntity orderEntity);
}
