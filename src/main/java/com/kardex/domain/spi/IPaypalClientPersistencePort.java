package com.kardex.domain.spi;

public interface IPaypalClientPersistencePort {
    boolean verifyWebhookEvent(String transmissionId, String transmissionTime, String certUrl, String transmissionSig, String authAlgo, String requestBody);
    String createOrder(double amount, String currency, Long cartId, String returnUrl, String cancelUrl);
}
