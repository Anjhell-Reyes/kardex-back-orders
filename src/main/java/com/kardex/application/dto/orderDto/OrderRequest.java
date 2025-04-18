package com.kardex.application.dto.orderDto;

import com.kardex.domain.utils.Constants;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderRequest {

    @NotNull(message = Constants.PRODUCT_ID_NOT_BLANK_MESSAGE)
    private Long productId;

    @NotNull(message = Constants.QUANTITY_NOT_BLANK_MESSAGE)
    @Positive(message = Constants.QUANTITY_POSITIVE_MESSAGE)
    private Integer quantity;
}
