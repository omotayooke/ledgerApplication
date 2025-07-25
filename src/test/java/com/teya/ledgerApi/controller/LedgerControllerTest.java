package com.teya.ledgerApi.controller;

import com.teya.ledgerApi.service.LedgerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LedgerController.class)
class LedgerControllerTest {

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public LedgerService ledgerService() {
            return Mockito.mock(LedgerService.class);
        }
    }

    @Test
    void testDeposit_Successful() throws Exception {
        mockMvc.perform(post("/api/ledger/deposit")
                        .param("description", "deposit")
                        .param("amount", "100.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Deposit successful"));
    }

    @Test
    void testDeposit_InvalidAmount() throws Exception {
        mockMvc.perform(post("/api/ledger/deposit")
                        .param("description", "Deposit")
                        .param("amount", "-10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Amount must be a positive number"));
    }

    @Test
    void testDeposit_DescriptionIsNull() throws Exception {
        mockMvc.perform(post("/api/ledger/deposit")
                        .param("description", "")
                        .param("amount", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Description must not be empty"));
    }

    @Test
    void testDeposit_AmountIsNull() throws Exception {
        mockMvc.perform(post("/api/ledger/deposit")
                        .param("description", "deposit")
                        .param("amount", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Amount must not be null or blank"));
    }

    @Test
    void testWithdraw_Successful() throws Exception {
        ledgerService.deposit("deposit", "200");
        mockMvc.perform(post("/api/ledger/withdraw")
                        .param("description", "withdraw")
                        .param("amount", "100.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Withdrawal successful"));
    }

    @Test
    void testWithdraw_InvalidAmount() throws Exception {
        mockMvc.perform(post("/api/ledger/withdraw")
                        .param("description", "withdraw")
                        .param("amount", "-10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Amount must be a positive number"));
    }

    @Test
    void testWithdraw_DescriptionIsNull() throws Exception {
        mockMvc.perform(post("/api/ledger/withdraw")
                        .param("description", "")
                        .param("amount", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Description must not be empty"));
    }

    @Test
    void testWithdraw_AmountIsNull() throws Exception {
        mockMvc.perform(post("/api/ledger/withdraw")
                        .param("description", "withdraw")
                        .param("amount", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Amount must not be null or blank"));
    }
}