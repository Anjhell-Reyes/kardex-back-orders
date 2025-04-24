package com.kardex.domain.api;

import com.kardex.domain.model.CustomPage;
import com.kardex.domain.model.Order;
import com.kardex.domain.model.OrderStatusHistory;

import java.io.File;
import java.util.List;

public interface IOrderServicePort {
    void saveOrder(Long cartId, File archivo);

    Order getOrder(Long orderId, String tokenOrder);

    CustomPage<Order> getAllOrders(String userId, int page, int size, String sortBy, boolean asc);

    void updateOrder(Long orderId, Order order);

    List<OrderStatusHistory> getOrderHistory(Long orderId);

    void deleteOrder(String userId, Long orderId);
}
