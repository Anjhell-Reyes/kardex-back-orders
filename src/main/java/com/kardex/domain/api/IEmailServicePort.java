package com.kardex.domain.api;

import com.kardex.domain.model.EmailRequest;

import java.util.List;

public interface IEmailServicePort {

    void sendEmailToProvider(EmailRequest emailRequest);

    void sendEmailtoUser(String customerEmail, List<String> productNames);
}
