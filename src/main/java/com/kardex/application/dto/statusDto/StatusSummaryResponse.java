package com.kardex.application.dto.statusDto;

import com.kardex.domain.model.ProviderSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusSummaryResponse {
    private Long id;
    private String statusName;
}
