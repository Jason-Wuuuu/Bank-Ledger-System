package dev.codescreen.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public abstract class TransactionRequest {

    @NotBlank(message = "User ID cannot be blank")
    private String userId;

    @NotBlank(message = "Message ID cannot be blank")
    private String messageId;

    @Valid
    @NotNull(message = "Transaction amount cannot be null")
    private Amount transactionAmount;

    /**
     * Constructor for TransactionRequest.
     * Initializes the user ID, message ID, and transaction amount.
     *
     * @param userId            the user's identifier
     * @param messageId         the unique identifier for the message
     * @param transactionAmount the transaction amount details
     */
    public TransactionRequest(String userId, String messageId, Amount transactionAmount) {
        this.userId = userId;
        this.messageId = messageId;
        this.transactionAmount = transactionAmount;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Amount getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Amount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "userId='" + userId + '\'' +
                ", messageId='" + messageId + '\'' +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}


