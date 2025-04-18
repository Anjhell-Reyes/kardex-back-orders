package com.kardex.application.dto.cartDto;

import com.kardex.domain.utils.Constants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemRequest {
    @NotNull(message = Constants.PRODUCT_ID_NOT_BLANK_MESSAGE)
    private Long productId;

    @NotNull(message = Constants.QUANTITY_NOT_BLANK_MESSAGE)
    @Positive(message = Constants.QUANTITY_POSITIVE_MESSAGE)
    private Integer quantity;
}
