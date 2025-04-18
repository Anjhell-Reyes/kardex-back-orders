package com.kardex.application.dto.productDto;

import com.kardex.domain.model.ProviderSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductSummaryResponse {
    private Long id;
    private String name;
    private Double price;
    private String imageUrl;
    private ProviderSummaryResponse provider;
}
