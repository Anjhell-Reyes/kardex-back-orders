package com.kardex.domain.usecase;

import com.kardex.domain.api.IEmailServicePort;
import com.kardex.domain.model.EmailRequest;
import com.kardex.domain.spi.IEmailPersistencePort;
import com.kardex.domain.utils.Constants;

import java.util.List;

public class EmailUseCase implements IEmailServicePort {

    private final IEmailPersistencePort emailPersistencePort;

    public EmailUseCase(IEmailPersistencePort emailPersistencePort) {
        this.emailPersistencePort = emailPersistencePort;
    }

    @Override
    public void sendEmailToProvider(EmailRequest emailRequest) {
        String contentProvider = Constants.EMAIL_PROVIDER_CONTENT(emailRequest.getOrderId(), emailRequest.getOrderNumber(), emailRequest.getOrderToken(), emailRequest.getItems());

        emailPersistencePort.sendEmail(emailRequest.getProviderEmail(),contentProvider,true );
    }

    @Override
    public void sendEmailtoUser(String customerEmail, List<String> productNames) {
        String contentUser = Constants.EMAIL_USER_CONTENT(productNames);
        emailPersistencePort.sendEmail(customerEmail,contentUser,false);

    }
}
