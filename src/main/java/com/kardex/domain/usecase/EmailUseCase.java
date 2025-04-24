package com.kardex.domain.usecase;

import com.kardex.domain.api.IEmailServicePort;
import com.kardex.domain.model.EmailRequest;
import com.kardex.domain.spi.IEmailPersistencePort;
import com.kardex.domain.utils.Constants;

import java.io.File;
import java.util.List;

public class EmailUseCase implements IEmailServicePort {

    private final IEmailPersistencePort emailPersistencePort;

    public EmailUseCase(IEmailPersistencePort emailPersistencePort) {
        this.emailPersistencePort = emailPersistencePort;
    }

    @Override
    public void sendEmailToProvider(EmailRequest emailRequest, File archivo) {
        String contentProvider = Constants.EMAIL_PROVIDER_CONTENT(emailRequest.getOrderId(), emailRequest.getOrderNumber(), emailRequest.getOrderToken(), emailRequest.getItems());

        emailPersistencePort.sendEmail(emailRequest.getProviderEmail(),contentProvider,true, archivo );
    }

    @Override
    public void sendEmailtoUser(String customerEmail, List<String> productNames, File archivo) {
        String contentUser = Constants.EMAIL_USER_CONTENT(productNames);
        emailPersistencePort.sendEmail(customerEmail,contentUser,false, archivo);
    }
}
