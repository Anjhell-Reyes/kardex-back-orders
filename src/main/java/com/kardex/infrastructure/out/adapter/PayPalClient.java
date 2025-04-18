package com.kardex.infrastructure.out.adapter;

import com.kardex.domain.spi.IPaypalClientPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PayPalClient implements IPaypalClientPersistencePort {

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.client-secret}")
    private String clientSecret;

    @Value("${paypal.api-base}")
    private String apiBase; // puede ser https://api-m.sandbox.paypal.com

    @Value("${paypal.webhook-id}")
    private String webhookId;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public boolean verifyWebhookEvent( String transmissionId, String transmissionTime, String certUrl, String transmissionSig, String authAlgo,String requestBody) {
        try {
            String accessToken = obtainAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> verificationBody = Map.of(
                    "transmission_id", transmissionId,
                    "transmission_time", transmissionTime,
                    "cert_url", certUrl,
                    "auth_algo", authAlgo,
                    "transmission_sig", transmissionSig,
                    "webhook_id", webhookId,
                    "webhook_event", objectMapper.readValue(requestBody, Object.class)
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(verificationBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    apiBase + "/v1/notifications/verify-webhook-signature",
                    entity,
                    Map.class
            );
            return "SUCCESS".equals(response.getBody().get("verification_status"));
        } catch (Exception e) {
            System.err.println("Error verifying PayPal event: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String createOrder(double amount, String currency, Long cartId, String returnUrl, String cancelUrl) {
        String accessToken = obtainAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> amountMap = Map.of(
                "currency_code", currency,
                "value", String.format("%.2f", amount)
        );

        Map<String, Object> purchaseUnit = Map.of(
                "amount", amountMap,
                "custom_id", cartId
        );

        Map<String, Object> applicationContext = Map.of(
                "return_url", returnUrl,
                "cancel_url", cancelUrl
        );

        Map<String, Object> requestBody = Map.of(
                "intent", "CAPTURE",
                "purchase_units", List.of(purchaseUnit),
                "application_context", applicationContext
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                apiBase + "/v2/checkout/orders",
                request,
                Map.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return (String) response.getBody().get("id");
        } else {
            throw new RuntimeException("Error al crear la orden de PayPal");
        }
    }

    private String obtainAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                apiBase + "/v1/oauth2/token",
                request,
                Map.class
        );

        return (String) response.getBody().get("access_token");
    }
}

