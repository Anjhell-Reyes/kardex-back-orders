package com.kardex.infrastructure.out.adapter;

import com.kardex.domain.model.OrderStatusHistory;
import com.kardex.domain.spi.IOrderStatusHistoryPersistencePort;
import com.kardex.infrastructure.out.entity.OrderStatusHistoryEntity;
import com.kardex.infrastructure.out.mapper.OrderStatusHistoryEntityMapper;
import com.kardex.infrastructure.out.repository.IOrderStatusHistoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderStatusHistoryJpaAdapter implements IOrderStatusHistoryPersistencePort {

    private final IOrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrderStatusHistoryEntityMapper orderStatusHistoryEntityMapper;

    @Override
    public void saveOrderStatusHistory(OrderStatusHistory order) {
        orderStatusHistoryRepository.save(orderStatusHistoryEntityMapper.toEntity(order));
    }

    @Override
    public List<OrderStatusHistory> getAllStatusHistoryByOrderId(Long orderId){
        List<OrderStatusHistoryEntity> orderStatusHistoryEntities = orderStatusHistoryRepository.findByOrderId(orderId);
        return orderStatusHistoryEntities.stream()
                .map(orderStatusHistoryEntityMapper::toOrder)
                .collect(Collectors.toList());
    }
}
