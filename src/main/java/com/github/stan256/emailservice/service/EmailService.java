package com.github.stan256.emailservice.service;

import com.github.stan256.emailservice.model.EmailModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class EmailService {
    @Value("${email.from}")
    private String emailSender;

    private final List<JavaMailSenderImpl> emailProviders;

    public EmailService(List<JavaMailSenderImpl> emailProviders) {
        this.emailProviders = emailProviders;
    }

    @Async
    public CompletableFuture<Boolean> sendEmail(EmailModel emailModel) {
        if (!validEmail(emailModel)) {
            log.info("Email could not be send due to invalidity of request");
            return CompletableFuture.completedFuture(false);
        }

        for (JavaMailSenderImpl provider : emailProviders) {
            try {
                SimpleMailMessage email = convertEmailData(emailModel);
                provider.send(email);
                log.info(String.format("Email to %s has been successfully sent to provider", Arrays.toString(email.getTo())));
                return CompletableFuture.completedFuture(true);
            } catch (MailSendException e) {
                log.error("Error during the attempt to send an email via provider: " + provider.getHost(), e);
            }
        }

        log.error("All email providers are not able to process the email sending");
        return CompletableFuture.completedFuture(false);
    }

    boolean validEmail(EmailModel emailModel) {
        // todo add checking that recipients && cc's strings are really an emails (using regex)
        return emailModel.getRecipients() != null && !emailModel.getRecipients().isEmpty() &&
                emailModel.getSubject() != null && !emailModel.getSubject().isEmpty() &&
                emailModel.getBody() != null && !emailModel.getBody().isEmpty();
    }

    SimpleMailMessage convertEmailData(EmailModel emailModel) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailSender);
        simpleMailMessage.setSubject(emailModel.getSubject());
        simpleMailMessage.setText(emailModel.getBody());

        if (emailModel.getCc() != null) {
            simpleMailMessage.setCc(emailModel.getCc().toArray(new String[0]));
        }

        simpleMailMessage.setTo(emailModel.getRecipients().toArray(new String[0]));
        return simpleMailMessage;
    }
}
