package com.github.stan256.emailservice.configuration.providers;

import lombok.Data;

@Data
public abstract class EmailProviderConfig {
    protected String host;
    protected int port;
    protected String username;
    protected String password;
}
