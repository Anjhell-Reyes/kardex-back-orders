package com.kardex.infrastructure.input;

import com.kardex.application.handler.payPalHandler.IPayPalHandler;
import com.kardex.domain.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(Constants.PAYPAL_BASE_PATH)
@RequiredArgsConstructor
public class PayPalRestController {

    private final IPayPalHandler payPalHandler;

    @Operation(summary = "create order in paypal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.CREATED, description = "Order created", content = @Content),
    })
    @PostMapping(Constants.CREATE_ORDER)
    public ResponseEntity<Map<String, String>> crearOrden(@RequestParam Long cartId) {
        String orderId = payPalHandler.startPay(cartId);
        Map<String, String> response = new HashMap<>();
        response.put("orderId", orderId);
        return ResponseEntity.ok(response);
    }
}
