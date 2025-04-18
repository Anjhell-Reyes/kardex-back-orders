package com.kardex.application.dto.cartDto;

import com.kardex.application.dto.productDto.ProductSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemResponse {
    private Long id;
    private Integer quantity;
    private ProductSummaryResponse product;
}
