package com.github.stan256.emailservice.controller;

import com.github.stan256.emailservice.model.EmailData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("email")
public class EmailController {

    @PostMapping("send")
    public void sendEmail(EmailData emailData) {

    }

}
