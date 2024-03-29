package com.github.stan256.emailservice.service;

import com.github.stan256.emailservice.model.EmailModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest
public class EmailServiceTest {
    private EmailService service;

    @Mock
    @Qualifier("sendgrid")
    private JavaMailSenderImpl sendgrid;

    @Mock
    @Qualifier("mailjet")
    private JavaMailSenderImpl mailjet;

    @BeforeEach
    public void initService() {
        this.service = new EmailService(Arrays.asList(mailjet, sendgrid));
    }

    @Test
    public void firstProviderFailoverScenario() throws InterruptedException, ExecutionException {
        EmailModel model = testEmail();
        SimpleMailMessage testMessage = service.convertEmailData(model);

        Mockito.doThrow(new MailSendException("Error scenario...")).when(mailjet).send(testMessage);
        Mockito.doNothing().when(sendgrid).send(testMessage);

        CompletableFuture<Boolean> future = service.sendEmail(model);

        Boolean bool = future.get();
        Assertions.assertTrue(bool);
        verify(mailjet, times(1)).send(testMessage);
        verify(sendgrid, times(1)).send(testMessage);
    }

    @Test
    public void allProvidersAreNotAvailable() throws ExecutionException, InterruptedException {
        EmailModel model = testEmail();
        SimpleMailMessage testMessage = service.convertEmailData(model);

        Mockito.doThrow(new MailSendException("Error scenario...")).when(mailjet).send(testMessage);
        Mockito.doThrow(new MailSendException("Error scenario...")).when(sendgrid).send(testMessage);

        CompletableFuture<Boolean> future = service.sendEmail(model);

        Boolean bool = future.get();
        assertFalse(bool);
    }

    private EmailModel testEmail() {
        EmailModel model = new EmailModel();
        model.setBody("Test body");
        model.setSubject("Test subject");
        model.setCc(Arrays.asList("test@email.one", "test@email.two"));
        model.setRecipients(Arrays.asList("test@email.three", "test@email.four"));
        return model;
    }
}