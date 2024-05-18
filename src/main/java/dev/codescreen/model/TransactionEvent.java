package dev.codescreen.model;

import java.time.LocalDateTime;

public class TransactionEvent {
    /*
        Implement the event sourcing pattern to record all banking transactions as immutable events.
        Each event should capture relevant information such as transaction type, amount, timestamp, and account identifier.
        Define the structure of events and ensure they can be easily serialized and persisted to a data store of your choice.
     */

    private final TransactionRequest transactionRequest;

    private final TransactionType type;  // "LOAD" / "AUTHORIZATION"
    private final LocalDateTime timestamp;
    private final ResponseCode responseCode; // "APPROVED" / "DECLINED"

    /**
     * Constructor for creating a transaction event.
     *
     * @param transactionRequest the transaction request details associated with this event
     * @param type               the type of the transaction (LOAD or AUTHORIZATION)
     * @param timestamp          the timestamp at which the transaction occurred
     * @param responseCode       the result of the transaction (APPROVED or DECLINED)
     */
    public TransactionEvent(TransactionRequest transactionRequest, TransactionType type, LocalDateTime timestamp, ResponseCode responseCode) {
        this.transactionRequest = transactionRequest;
        this.type = type;
        this.timestamp = timestamp;
        this.responseCode = responseCode;
    }

    // Getters
    public TransactionRequest getTransactionRequest() {
        return transactionRequest;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    @Override
    public String toString() {
        return "TransactionEvent{" +
                "transactionRequest=" + transactionRequest +
                ", type=" + type +
                ", timestamp=" + timestamp +
                ", responseCode=" + responseCode +
                '}';
    }
}

