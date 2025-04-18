package com.kardex.application.handler.payPalHandler;

import com.kardex.domain.api.ICartServicePort;
import com.kardex.domain.spi.IPaypalClientPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayPalHandler implements IPayPalHandler {

    private final IPaypalClientPersistencePort paypalClient;
    private final ICartServicePort cartServicePort;

    @Override
    public String startPay(Long cartId) {
        double total = cartServicePort.getTotal(cartId);

        return paypalClient.createOrder(
                total,
                "USD",
                cartId,
                "http://127.0.0.1:5501/kardex.html",
                "http://127.0.0.1:5501/kardex.html"
        );
    }
}
