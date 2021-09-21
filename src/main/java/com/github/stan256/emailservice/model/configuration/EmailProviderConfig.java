package com.github.stan256.emailservice.model.configuration;

import lombok.Data;

@Data
public abstract class EmailProviderConfig {
    protected String host;
    protected int port;
    protected String username;
    protected String password;
}
