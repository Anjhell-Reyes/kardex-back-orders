package com.kardex.domain.spi;

import com.kardex.domain.model.OrderStatusHistory;

import java.util.List;

public interface IOrderStatusHistoryPersistencePort {

    void saveOrderStatusHistory(OrderStatusHistory history);

    List<OrderStatusHistory> getAllStatusHistoryByOrderId(Long orderId);
}
