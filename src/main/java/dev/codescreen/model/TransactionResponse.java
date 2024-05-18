package dev.codescreen.model;

public abstract class TransactionResponse {
    private String userId;
    private String messageId;
    private Amount balance;

    /**
     * Constructor for TransactionResponse.
     * Initializes the user ID, message ID, and current balance which reflects the state after the transaction.
     *
     * @param userId     the user's identifier
     * @param messageId  the unique identifier for the transaction message
     * @param balance    the current balance represented as an Amount object
     */
    public TransactionResponse(String userId, String messageId, Amount balance) {
        this.userId = userId;
        this.messageId = messageId;
        this.balance = balance;
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

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }
}
