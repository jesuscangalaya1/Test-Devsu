package com.devsu.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AccountServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private String accountJson;

    @BeforeEach
    void setUp() {
        accountJson = "{ \"clientId\": 2, \"accountNumber\": \"123456\", \"accountType\": \"SAVINGS\", \"initialBalance\": 1000.0 }";
    }

    @Test
    @DisplayName("Should return all accounts when GET /api/accounts is called")
    void shouldReturnAllAccounts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/accounts")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should create an account when POST /api/accounts is called with valid data")
    void shouldCreateAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts")
                        .contentType("application/json")
                        .content(accountJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}