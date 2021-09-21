package com.github.stan256.emailservice.controller;

import com.github.stan256.emailservice.model.EmailModel;
import com.github.stan256.emailservice.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("send")
    public void sendEmail(@RequestBody EmailModel emailModel) {
        emailService.sendEmail(emailModel);
    }

}
