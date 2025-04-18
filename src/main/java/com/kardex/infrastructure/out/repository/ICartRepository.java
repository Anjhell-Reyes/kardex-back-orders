package com.kardex.infrastructure.out.repository;

import com.kardex.infrastructure.out.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByUserId(String userId);
}
