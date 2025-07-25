package com.teya.ledgerApi.controller;

import com.teya.ledgerApi.model.Transaction;
import com.teya.ledgerApi.service.LedgerService;
import com.teya.ledgerApi.service.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ledger")
public class LedgerController {
    private final LedgerService ledgerService;

    public LedgerController(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam String description, @RequestParam String amount) {
        validateRequest(description, amount);
        ledgerService.deposit(description, amount);
        return ResponseEntity.ok(Map.of("message","Deposit successful"));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam String description, @RequestParam String amount) {
        validateRequest(description, amount);
            ledgerService.withdraw(description, amount);
            return ResponseEntity.ok(Map.of("message", "Withdrawal successful"));
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance() {
        return ResponseEntity.ok(ledgerService.getBalance());
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactions() {
        return ResponseEntity.ok(ledgerService.getTransactionHistory());
    }

    private void validateRequest(String description, String amountString){
        List<String> errors = new ArrayList<>();

        if (description == null || description.trim().isEmpty()) {
            errors.add("Description must not be empty");
        }

        if (amountString == null || amountString.trim().isEmpty()) {
            errors.add("Amount must not be null or blank");
        } else {
            try {
                BigDecimal amount = new BigDecimal(amountString);
                if (amount.signum() <= 0) {
                    errors.add("Amount must be a positive number");
                }
                if (amount.scale() > 2) {
                    errors.add("Amount must not have more than 2 decimal places");
                }
            } catch (NumberFormatException ex) {
                errors.add("Amount must be a valid");
            }
        }
        if (!errors.isEmpty()) {
                throw new ValidationException(errors);
        }
    }

}
