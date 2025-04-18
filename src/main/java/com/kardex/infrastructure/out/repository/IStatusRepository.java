package com.kardex.infrastructure.out.repository;


import com.kardex.infrastructure.out.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStatusRepository extends JpaRepository<StatusEntity, Long> {
}
