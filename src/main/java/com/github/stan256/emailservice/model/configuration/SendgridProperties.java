package com.github.stan256.emailservice.model.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="email.providers.sendgrid")
@Configuration
public class SendgridProperties extends EmailProviderConfig {
}