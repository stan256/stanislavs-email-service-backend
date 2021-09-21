package com.github.stan256.emailservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/health_check")
public class HealthCheckController {

    final static String SUCCESSFUL_STATUS = "All works fine! ";

    @GetMapping("get")
    public String getHealthCheck() {
        return SUCCESSFUL_STATUS + LocalDateTime.now();
    }

}
