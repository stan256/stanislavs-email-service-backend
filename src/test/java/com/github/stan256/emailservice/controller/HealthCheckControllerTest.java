package com.github.stan256.emailservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.github.stan256.emailservice.controller.HealthCheckController.SUCCESSFUL_STATUS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthCheckController.class)
public class HealthCheckControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getHealthCheck() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/health_check/get"))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertTrue(mvcResult.getResponse().getContentAsString().startsWith(SUCCESSFUL_STATUS));
    }
}