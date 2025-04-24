package com.kardex.application.handler.payPalHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kardex.application.dto.paypalDto.PayPalWebhookEvent;
import com.kardex.domain.api.IOrderServicePort;
import com.kardex.domain.spi.IComprobanteGeneratorPersistencePort;
import com.kardex.domain.spi.IPaypalClientPersistencePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentHandler implements IPaymentHandler {

    private final IOrderServicePort orderServicePort;
    private final IPaypalClientPersistencePort payPalClientPersistencePort;
    private final IComprobanteGeneratorPersistencePort comprobanteGeneratorPersistencePort;
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(PaymentHandler.class);

    @Override
    public void processPayPalWebhook(String requestBody, String transmissionId, String transmissionTime, String certUrl, String authAlgo, String transmissionSig) {
        try {
            PayPalWebhookEvent event = objectMapper.readValue(requestBody, PayPalWebhookEvent.class);
            PayPalWebhookEvent.Resource resource = event.getResource();

            String eventId = event.getId();
            String customId = event.getResource().getCustomId();
            String orderId = resource.getTransactionId();
            String amount = resource.getAmount().getValue();
            String currency = resource.getAmount().getCurrencyCode();
            String buyerName = resource.getPayee().getEmail();
            String date = resource.getCreateTime();

            Long cartId = Long.parseLong(customId);

            boolean isValid = payPalClientPersistencePort.verifyWebhookEvent(
                transmissionId, transmissionTime, certUrl, transmissionSig, authAlgo, requestBody
            );

            if (isValid) {
                logger.info("Webhook valid. Creating order...");
                String ruta = "comprobantes/comprobante_" + orderId + ".pdf";
                File archivo = new File(ruta);

                comprobanteGeneratorPersistencePort.generarComprobante(buyerName, currency + " " + amount, orderId, date, ruta);

                orderServicePort.saveOrder(cartId, archivo);
            } else {
                logger.warn("Invalid webhook or cartId. Skipping order creation.");
            }
        } catch (JsonProcessingException e) {
            logger.error("Error parsing PayPal webhook event: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error processing PayPal webhook: {}", e.getMessage());
        }
    }
}
