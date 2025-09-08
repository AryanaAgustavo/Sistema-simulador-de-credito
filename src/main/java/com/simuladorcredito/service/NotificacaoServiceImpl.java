package com.simuladorcredito.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public abstract class NotificacaoServiceImpl implements NotificacaoService {

    private final JavaMailSender mailSender;
    private static final Logger LOGGER = Logger.getLogger(NotificacaoServiceImpl.class.getName());

    @Autowired
    public NotificacaoServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void notificarClientePorEmail(String email, String assuntoEmail, String mensagem) {
        try {
            /*MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject(assuntoEmail);
            helper.setText(mensagem, true);

            mailSender.send(message);*/
            LOGGER.info("E-mail de notificação enviado para: " + email);
            LOGGER.info("Assunto: " + assuntoEmail);
            LOGGER.info("Mensagem: " + mensagem);
        } catch (Exception e) {
            LOGGER.severe("Falha ao enviar e-mail para: " + email);
        }
    }
}
