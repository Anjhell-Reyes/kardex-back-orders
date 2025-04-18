package com.kardex.infrastructure.out.repository;


import com.kardex.infrastructure.out.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    Page<OrderEntity> findAllByUserId(String userId, Pageable pageable);
}
