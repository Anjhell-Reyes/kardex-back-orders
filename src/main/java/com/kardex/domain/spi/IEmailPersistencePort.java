package com.kardex.domain.spi;

public interface IEmailPersistencePort {
    void sendEmail(String to, String content, boolean isProvider);
}
