package com.github.stan256.emailservice.configuration.providers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="email.providers.sendgrid")
@Configuration
public class SendgridProperties extends EmailProviderConfig {
}