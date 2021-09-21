package com.github.stan256.emailservice.configuration;

import com.github.stan256.emailservice.configuration.providers.EmailProviderConfig;
import com.github.stan256.emailservice.configuration.providers.MailjetProperties;
import com.github.stan256.emailservice.configuration.providers.SendgridProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Slf4j
@Configuration
public class EmailProvidersConfiguration {
    private final SendgridProperties sendgridProperties;
    private final MailjetProperties mailjetProperties;

    public EmailProvidersConfiguration(SendgridProperties sendgridProperties,
                                       MailjetProperties mailjetProperties) {
        this.sendgridProperties = sendgridProperties;
        this.mailjetProperties = mailjetProperties;
    }

    @Bean
    @Order(1)
    @Qualifier("mailjet")
    public JavaMailSenderImpl mailjetProvider() {
        JavaMailSenderImpl javaMailSender = initializeMailSender(mailjetProperties);
        log.info("Email provider initialized: " + mailjetProperties.getHost());
        return javaMailSender;
    }

    @Bean
    @Order(2)
    @Qualifier("sendgrid")
    public JavaMailSenderImpl sendgridProvider() {
        JavaMailSenderImpl javaMailSender = initializeMailSender(sendgridProperties);
        log.info("Email provider initialized: " + sendgridProperties.getHost());
        return javaMailSender;
    }

    private JavaMailSenderImpl initializeMailSender(EmailProviderConfig mailgunConfig) {
        JavaMailSenderImpl mailgunMailSender = new JavaMailSenderImpl();
        mailgunMailSender.setHost(mailgunConfig.getHost());
        mailgunMailSender.setPort(mailgunConfig.getPort());
        mailgunMailSender.setUsername(mailgunConfig.getUsername());
        mailgunMailSender.setPassword(mailgunConfig.getPassword());

        Properties props = mailgunMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailgunMailSender;
    }
}
