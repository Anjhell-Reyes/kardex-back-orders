package com.kardex.infrastructure.input;

import com.kardex.application.handler.payPalHandler.IPaymentHandler;
import com.kardex.domain.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.WEBHOOK_BASE_PATH)
@RequiredArgsConstructor
public class WebhookRestController {

    private final IPaymentHandler paymentHandler;

    @Operation(summary = "Handle PayPal Webhook event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Webhook processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid webhook event", content = @Content)
    })
    @PostMapping()
    public ResponseEntity<Void> handleWebhook(
            @RequestBody String requestBody,
            @RequestHeader("Paypal-Transmission-Id") String transmissionId,
            @RequestHeader("Paypal-Transmission-Time") String transmissionTime,
            @RequestHeader("Paypal-Cert-Url") String certUrl,
            @RequestHeader("Paypal-Auth-Algo") String authAlgo,
            @RequestHeader("Paypal-Transmission-Sig") String transmissionSig
    ) {
        paymentHandler.processPayPalWebhook(requestBody, transmissionId, transmissionTime, certUrl, authAlgo, transmissionSig);
        return ResponseEntity.ok().build();
    }
}
