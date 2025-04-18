package com.kardex.infrastructure.out.adapter;

import com.kardex.domain.spi.INotificationPersistencePort;
import com.kardex.infrastructure.out.feign.INotificationClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationServiceAdapter implements INotificationPersistencePort {

    private final INotificationClient notificationClient;

    @Override
    public void sendNotification(String userId, String productName, String orderStatus) {
        notificationClient.saveOrderNotification(userId, productName, orderStatus);
    }
}
