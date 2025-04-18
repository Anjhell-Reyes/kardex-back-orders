package com.kardex.application.handler.orderHandler;

import com.kardex.application.dto.orderDto.*;
import com.kardex.application.mapper.OrderMapper;
import com.kardex.domain.api.IOrderServicePort;
import com.kardex.domain.model.CustomPage;
import com.kardex.domain.model.Order;
import com.kardex.domain.model.OrderStatusHistory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse getOrder(Long orderId, String tokenOrder) {
        return orderMapper.toResponse(orderServicePort.getOrder(orderId, tokenOrder));
    }

    @Override
    public Page<OrderPaginated> getAllOrders(HttpServletRequest request, int page, int size, String sortBy, boolean asc) {
        String userId= (String) request.getAttribute("userId");

        CustomPage<Order> customPage = orderServicePort.getAllOrders(userId, page, size, sortBy, asc);

        List<OrderPaginated> paginatedOrders = customPage.getContent().stream()
                .map(orderMapper::toOrderPaginated)
                .collect(Collectors.toList());

        return new PageImpl<>(paginatedOrders, PageRequest.of(customPage.getPageNumber(), customPage.getPageSize()), customPage.getTotalElements());
    }

    @Override
    public List<OrderStatusHistoryResponse> getOrderHistory(Long orderId) {
        List<OrderStatusHistory> orderStatusHistory = orderServicePort.getOrderHistory(orderId);
        return orderStatusHistory.stream()
                .map(orderMapper::toOrderStatusHistoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateOrder(Long orderId, OrderUpdateRequest orderRequest) {
        orderServicePort.updateOrder(orderId, orderMapper.toOrder(orderRequest));
    }

    @Override
    public void deleteOrder(HttpServletRequest request, Long orderId) {
        String userId = (String) request.getAttribute("userId");

        orderServicePort.deleteOrder(userId, orderId);
    }
}
