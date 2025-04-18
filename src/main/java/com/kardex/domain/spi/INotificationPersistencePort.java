package com.kardex.domain.spi;

public interface INotificationPersistencePort {
    void sendNotification(String userId, String productName, String orderStatus);
}
