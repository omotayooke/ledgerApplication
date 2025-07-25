package com.teya.ledgerApi.service;

import com.teya.ledgerApi.model.Transaction;
import com.teya.ledgerApi.model.TransactionType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class LedgerService {
    private final List<Transaction> transactions = new CopyOnWriteArrayList<>();
    private BigDecimal balance = BigDecimal.ZERO;

    public synchronized void deposit(String description, String amountString) {
        BigDecimal amount = roundedAmount(amountString);
        balance = balance.add(amount);
        transactions.add(new Transaction(description, TransactionType.DEPOSIT, amount));
    }

    public synchronized void withdraw(String description, String amountString) {
        BigDecimal amount = roundedAmount(amountString);
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        balance = balance.subtract(amount);
        transactions.add(new Transaction(description, TransactionType.WITHDRAWAL, amount));
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactions);
    }

    private BigDecimal roundedAmount(String amountString){
        BigDecimal amount = new BigDecimal(amountString);
        if (amount.scale() != 2) {
            amount = amount.setScale(2, RoundingMode.HALF_UP);
        }
        return amount;
    }

}
