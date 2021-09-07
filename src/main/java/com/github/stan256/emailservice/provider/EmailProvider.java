package com.github.stan256.emailservice.provider;

import com.github.stan256.emailservice.model.EmailData;

public interface EmailProvider {
    void sendEmail(EmailData emailData);
}
