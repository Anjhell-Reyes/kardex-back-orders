package com.kardex.infrastructure.out.repository;

import com.kardex.infrastructure.out.entity.OrderStatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderStatusHistoryRepository extends JpaRepository<OrderStatusHistoryEntity, Long> {
    List<OrderStatusHistoryEntity> findByOrderId(Long orderId);
}
