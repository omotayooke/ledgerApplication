package com.teya.ledgerApi.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private static int idCounter = 1;
    private int id;
    private String description;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public Transaction(String description, TransactionType type, BigDecimal amount) {
        this.id = idCounter++;
        this.description = description;
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }
}
