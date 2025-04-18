package com.kardex.application.dto.orderDto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderStatusHistoryResponse {
    private Long id;
    private Long statusId;
    private LocalDateTime changedAt;
}
