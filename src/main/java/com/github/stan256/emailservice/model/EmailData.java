package com.github.stan256.emailservice.model;

import lombok.Data;

import java.util.List;

@Data
public class EmailData {
    private String subject;
    private List<String> recipients;
    private List<String> cc;
    private String body;
}
