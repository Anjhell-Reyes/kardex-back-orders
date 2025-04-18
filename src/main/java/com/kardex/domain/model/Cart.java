package com.kardex.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {
    private Long id;
    private String userId;
    private String customerEmail;
    private List<CartItem> items = new ArrayList<>();

    public Cart() {
        // Constructor vacío requerido por MapStruct
    }

    public Cart(String userId, String customerEmail) {
        this.userId = userId;
        this.customerEmail = customerEmail;
    }
    public Cart(Long id, String userId, String customerEmail, List<CartItem> items) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.customerEmail = customerEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
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

}
