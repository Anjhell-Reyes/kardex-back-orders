package com.kardex.domain.api;

import com.kardex.domain.model.EmailRequest;

import java.io.File;
import java.util.List;

public interface IEmailServicePort {

    void sendEmailToProvider(EmailRequest emailRequest, File archivo);

    void sendEmailtoUser(String customerEmail, List<String> productNames, File archivo);
}
