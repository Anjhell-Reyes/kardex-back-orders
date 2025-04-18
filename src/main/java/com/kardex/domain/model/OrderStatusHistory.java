package com.kardex.domain.model;

import java.time.LocalDateTime;

public class OrderStatusHistory {

    private Long id;
    private Order order;
    private Status status;
    private LocalDateTime changedAt;

    public OrderStatusHistory() {
    }

    public OrderStatusHistory(Long id, Order order, Status status, LocalDateTime changedAt) {
        this.id = id;
        this.order = order;
        this.status = status;
        this.changedAt = changedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
}
