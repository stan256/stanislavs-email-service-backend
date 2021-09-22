package com.github.stan256.emailservice.controller;

import com.github.stan256.emailservice.model.EmailModel;
import com.github.stan256.emailservice.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("send")
    public CompletableFuture<ResponseEntity<Boolean>> sendEmail(@RequestBody EmailModel emailModel) {
        return emailService
                .sendEmail(emailModel)
                .thenApply(bool -> bool ? ResponseEntity.ok(true): ResponseEntity.badRequest().body(false));
    }

}
