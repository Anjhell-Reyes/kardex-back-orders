package com.kardex.domain.model;

public class CartItem {
    private Long id;
    private Integer quantity;
    private Product product;
    //private Cart cart;

    public CartItem(Long id, Integer quantity, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
       // this.cart = cart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
