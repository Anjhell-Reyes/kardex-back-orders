package com.kardex.application.dto.cartDto;

import lombok.Data;

import java.util.List;

@Data
public class CartResponse {
    private Long id;
    private Double totalPrice;
    private List<CartItemResponse> items;
}
