package com.kardex.application.dto.paypalDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
public class PayPalWebhookEvent {

    @JsonProperty("id")
    private String id;

    @JsonProperty("event_type")
    private String eventType;

    private Resource resource;

    @Getter
    public static class Resource {

        @JsonProperty("custom_id")
        private String customId;

        @JsonProperty("status")
        private String status;

        @JsonProperty("purchase_units")
        private PurchaseUnit[] purchaseUnits;
    }

    @Getter
    public static class PurchaseUnit {

        @JsonProperty("reference_id")
        private String referenceId;

        private Map<String, Object> amount;
    }
}
