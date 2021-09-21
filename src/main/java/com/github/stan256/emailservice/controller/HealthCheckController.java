package com.github.stan256.emailservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/health_check")
public class HealthCheckController {

    @GetMapping("get")
    public String getHealthCheck() {
        return "All works fine! " + LocalDateTime.now();
    }

}
