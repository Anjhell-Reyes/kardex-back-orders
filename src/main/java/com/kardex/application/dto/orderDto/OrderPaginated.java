package com.kardex.application.dto.orderDto;

import com.kardex.application.dto.cartDto.CartItemResponse;
import com.kardex.application.dto.statusDto.StatusSummaryResponse;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderPaginated {
    private Long id;
    private List<CartItemResponse> items;
    private String userId;
    private StatusSummaryResponse status;
    private String customerEmail;
    private String numberOrder;
    private Double totalAmount;
    private String tokenOrder;
    private LocalDateTime createdAt;
}
