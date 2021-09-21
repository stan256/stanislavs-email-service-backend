package com.github.stan256.emailservice.configuration.providers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="email.providers.mailjet")
@Configuration
public class MailjetProperties extends EmailProviderConfig {
}