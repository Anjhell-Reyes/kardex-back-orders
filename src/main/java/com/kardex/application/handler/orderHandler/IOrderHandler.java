package com.kardex.application.handler.orderHandler;


import com.kardex.application.dto.orderDto.*;
import com.kardex.domain.model.OrderStatusHistory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderHandler {

    OrderResponse getOrder(Long orderId, String tokenOrder);

    List<OrderStatusHistoryResponse> getOrderHistory(Long orderId);

    void updateOrder(Long orderId, OrderUpdateRequest orderRequest);

    void deleteOrder(HttpServletRequest request, Long orderId);

    Page<OrderPaginated> getAllOrders(HttpServletRequest request, int page, int size, String sortBy, boolean asc);
}
