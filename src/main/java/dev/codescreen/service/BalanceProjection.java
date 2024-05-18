package dev.codescreen.service;

import dev.codescreen.model.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;

@Component
public class BalanceProjection {

    // map user's id with balance
    private final HashMap<String, BigDecimal> balances = new HashMap<>();

    /**
     * Projects and updates the balance based on the transaction event.
     * Adjusts the user's balance by either adding or subtracting the transaction amount,
     * depending on the transaction type and status.
     *
     * @param event the transaction event that affects the balance
     */
    public void project(TransactionEvent event) {
        TransactionRequest transactionRequest = event.getTransactionRequest();

        String userId = transactionRequest.getUserId();

        // get the amount to add / remove
        Amount transactionAmount = transactionRequest.getTransactionAmount();
        BigDecimal toAdd = new BigDecimal(transactionAmount.getAmount());

        if (event.getType() == TransactionType.LOAD) {
            // Loads (Credit)
            balances.merge(userId, toAdd, BigDecimal::add); // + toAdd
        }else if (event.getType() == TransactionType.AUTHORIZATION){
            // Authorizations (Debit)
            if (event.getResponseCode() == ResponseCode.APPROVED){
                // remove money if approved
                balances.merge(userId, toAdd.negate(), BigDecimal::add); // - toAdd
            }
        }
    }

    public BigDecimal getBalance(String userId) {
        return balances.getOrDefault(userId, BigDecimal.ZERO);
    }

    public boolean isSufficientBalance(String userId, BigDecimal deductionAmount) {
        return balances.getOrDefault(userId, BigDecimal.ZERO).compareTo(deductionAmount) >= 0;
    }
}