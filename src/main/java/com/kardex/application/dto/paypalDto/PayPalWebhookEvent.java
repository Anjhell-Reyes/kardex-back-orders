package com.kardex.application.dto.paypalDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PayPalWebhookEvent {

    @JsonProperty("id")
    private String id;

    @JsonProperty("event_type")
    private String eventType;

    private Resource resource;

    @Getter
    public static class Resource {

        @JsonProperty("id")
        private String transactionId;

        @JsonProperty("custom_id")
        private String customId;

        @JsonProperty("status")
        private String status;

        @JsonProperty("create_time")
        private String createTime;

        private Amount amount;

        private Payee payee;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Amount {
        @JsonProperty("currency_code")
        private String currencyCode;

        private String value;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Payee {
        @JsonProperty("email_address")
        private String email;
    }
}
