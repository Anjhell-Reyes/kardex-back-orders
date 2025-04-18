package com.kardex.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Order {

    private Long id;
    private List<CartItem> items;
    private String userId;
    private Status status;
    private String customerEmail;
    private String numberOrder;
    private Double totalAmount;
    private String tokenOrder;
    private LocalDateTime createdAt;

    public Order() {}

    public Order(Long id, List<CartItem> items, String userId, Status status, String customerEmail, String numberOrder, Double totalAmount, String tokenOrder, LocalDateTime createdAt) {
        this.id = id;
        this.items = items;
        this.userId = userId;
        this.status = status;
        this.customerEmail = customerEmail;
        this.numberOrder = numberOrder;
        this.totalAmount = totalAmount;
        this.tokenOrder = tokenOrder;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(String numberOrder) {
        this.numberOrder = numberOrder;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTokenOrder() {
        return tokenOrder;
    }

    public void setTokenOrder(String tokenOrder) {
        this.tokenOrder = tokenOrder;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Product> getProducts() {
        return items.stream()
                .map(CartItem::getProduct)
                .toList();
    }

    public List<Long> getProductIds() {
        return items.stream()
                .map(item -> item.getProduct().getId())
                .collect(Collectors.toList());
    }

    public List<String> getProvidersEmail() {
        return items.stream()
                .map(item -> item.getProduct().getProvider().getEmail())
                .collect(Collectors.toList());
    }
}