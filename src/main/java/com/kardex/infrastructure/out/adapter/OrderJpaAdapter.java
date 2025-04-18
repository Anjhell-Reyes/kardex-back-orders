package com.kardex.infrastructure.out.adapter;

import com.kardex.domain.exception.NotDataFoundException;
import com.kardex.domain.exception.OrderNotFoundException;
import com.kardex.domain.model.CustomPage;
import com.kardex.domain.model.Order;
import com.kardex.domain.spi.IOrderPersistencePort;
import com.kardex.infrastructure.out.entity.OrderEntity;
import com.kardex.infrastructure.out.mapper.OrderEntityMapper;
import com.kardex.infrastructure.out.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public Order saveOrder(Order order) {
        return orderEntityMapper.toOrder(orderRepository.save(orderEntityMapper.toEntity(order)));
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderEntityMapper.toOrder(orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new));
    }

    @Override
    public CustomPage<Order> getAllOrders(String userId, int offset, int limit, String sortBy, boolean asc) {
        Sort sort = Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(offset / limit, limit, sort);

        Page<OrderEntity> orderPage = orderRepository.findAllByUserId(userId, pageable);

        if (orderPage.isEmpty()) {
            throw new NotDataFoundException();
        }

        List<Order> orders = orderPage.getContent().stream()
                .map(orderEntityMapper::toOrder)
                .collect(Collectors.toList());

        return new CustomPage<>(
                orders,
                orderPage.getNumber(),
                orderPage.getSize(),
                orderPage.getTotalElements()
        );
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.save(orderEntityMapper.toEntity(order));
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
