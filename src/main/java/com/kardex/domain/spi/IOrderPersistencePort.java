package com.kardex.domain.spi;

import com.kardex.domain.model.CustomPage;
import com.kardex.domain.model.Order;
import jakarta.mail.MessagingException;

public interface IOrderPersistencePort {
    Order saveOrder(Order order);

    Order getOrder(Long orderId);

    CustomPage<Order> getAllOrders(String userId, int offset, int limit, String sortBy, boolean asc);

    void updateOrder(Order order);

    void deleteOrder(Long orderId);
}
