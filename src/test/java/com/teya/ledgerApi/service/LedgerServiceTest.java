package com.teya.ledgerApi.service;

import com.teya.ledgerApi.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class LedgerServiceTest {

    LedgerService ledgerService = new LedgerService();

    @BeforeEach
    void setUp() {
        ledgerService.deposit("deposit1", "200");
        ledgerService.deposit("deposit2", "100");
    }

    @Test
    void testDeposit() {
        Assertions.assertEquals(new BigDecimal("300.00"), ledgerService.getBalance());
    }

    @Test
    void testWithdraw() {
        ledgerService.withdraw("withdraw", "100.00");
        Assertions.assertEquals(new BigDecimal("200.00"), ledgerService.getBalance());
    }

    @Test
    void testBalance() {
        Assertions.assertEquals(new BigDecimal("300.00"), ledgerService.getBalance());
    }

    @Test
    void testGetTransactionHistory() {
        List<Transaction> result = ledgerService.getTransactionHistory();
        Assertions.assertEquals(2, result.size());
    }
}