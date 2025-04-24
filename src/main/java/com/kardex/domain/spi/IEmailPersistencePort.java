package com.kardex.domain.spi;

import java.io.File;

public interface IEmailPersistencePort {
    void sendEmail(String to, String content, boolean isProvider, File archivo);
}
