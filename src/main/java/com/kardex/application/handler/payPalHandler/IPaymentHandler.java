package com.kardex.application.handler.payPalHandler;

public interface IPaymentHandler {
    void processPayPalWebhook(String requestBody, String transmissionId, String transmissionTime, String certUrl, String authAlgo, String transmissionSig);
}
