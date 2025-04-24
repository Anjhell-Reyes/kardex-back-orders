package com.kardex.infrastructure.out.adapter;

import com.kardex.domain.exception.EmailSendingException;
import com.kardex.domain.spi.IEmailPersistencePort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailServiceAdapter implements IEmailPersistencePort {
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String content, boolean isProvider, File archivo)  {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);

            if (isProvider) {
                // 📦 Correo para el PROVEEDOR con botón de seguimiento
                helper.setSubject("🚚 Acción requerida: Gestionar un nuevo pedido");
            } else {
                // ✅ Correo para el USUARIO confirmando su pedido
                helper.setSubject("🛍️ ¡Pedido confirmado!");
            }

            helper.setText(content, true); // Activa HTML

            // 📎 Adjunta el archivo si existe
            if (archivo != null && archivo.exists()) {
                helper.addAttachment("Factura.pdf", archivo);
            }

            mailSender.send(message);
        }catch (MessagingException e){
            throw new EmailSendingException();
        }
    }
}