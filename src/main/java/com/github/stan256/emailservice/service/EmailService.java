package com.github.stan256.emailservice.service;

import com.github.stan256.emailservice.exception.AllProvidersNotAvailableException;
import com.github.stan256.emailservice.model.EmailModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
    @Retryable(value = AllProvidersNotAvailableException.class, maxAttempts = 3, backoff = @Backoff(delay = 60_000))
    public void sendEmail(EmailModel emailModel) {
        for (JavaMailSenderImpl provider : emailProviders) {
            try {
                SimpleMailMessage email = convertEmailData(emailModel);
                provider.send(email);
                log.info(String.format("Email to %s has been successfully sent", Arrays.toString(email.getTo())));
                return;
            } catch (MailSendException e) {
                log.error("Error during the attempt to send an email via provider: " + provider.getHost(), e);
            }
        }

        throw new AllProvidersNotAvailableException("All providers were not able to process the message correctly");
    }

    @Async
    @Recover
    public void recover(AllProvidersNotAvailableException e, EmailModel emailModel) {
        System.err.println(emailModel);
    }

    SimpleMailMessage convertEmailData(EmailModel emailModel) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailSender);
        simpleMailMessage.setSubject(emailModel.getSubject());
        simpleMailMessage.setText(emailModel.getBody());
        simpleMailMessage.setCc(emailModel.getCc().toArray(new String[0]));
        simpleMailMessage.setTo(emailModel.getRecipients().toArray(new String[0]));
        return simpleMailMessage;
    }
}
